package com.example.agentplatform.domain.node;

import com.example.agentplatform.domain.execution.NodeExecutionRequest;
import com.example.agentplatform.domain.execution.NodeExecutor;
import com.example.agentplatform.domain.execution.NodeResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Executes analytical SQL queries using DuckDB against CSV or Parquet inputs defined by node
 * configuration.
 */
@Component("data_analysis")
public class DataAnalysisNodeExecutor implements NodeExecutor {

    @Override
    /** {@inheritDoc} */
    public Mono<NodeResult> execute(final NodeExecutionRequest request) {
        return Mono.fromCallable(() -> runAnalysis(request.context()));
    }

    /**
     * Executes the configured SQL query using DuckDB.
     *
     * @param context merged workflow context
     * @return node result containing rows and metadata
     * @throws Exception if the query execution fails
     */
    private NodeResult runAnalysis(final Map<String, Object> context) throws Exception {
        final String filePath = context.getOrDefault("filePath", context.get("datasetPath")) == null
                ? null
                : context.getOrDefault("filePath", context.get("datasetPath")).toString();
        if (filePath == null) {
            throw new IllegalArgumentException("filePath or datasetPath is required");
        }
        final String query = context.getOrDefault("query", "SELECT * FROM data LIMIT 10").toString();
        try (Connection connection = DriverManager.getConnection("jdbc:duckdb:")) {
            connection.createStatement().execute("CREATE OR REPLACE VIEW data AS SELECT * FROM read_csv_auto('" + filePath + "')");
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    final List<Map<String, Object>> rows = new ArrayList<>();
                    final int columnCount = resultSet.getMetaData().getColumnCount();
                    while (resultSet.next()) {
                        final Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getObject(i));
                        }
                        rows.add(row);
                    }
                    final Map<String, Object> output = new HashMap<>();
                    output.put("rows", rows);
                    output.put("rowCount", rows.size());
                    output.put("query", query);
                    return new NodeResult(output, "Analyzed " + rows.size() + " rows");
                }
            }
        }
    }
}
