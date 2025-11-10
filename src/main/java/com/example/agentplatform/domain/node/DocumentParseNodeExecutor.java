package com.example.agentplatform.domain.node;

import com.example.agentplatform.domain.execution.NodeExecutionRequest;
import com.example.agentplatform.domain.execution.NodeExecutor;
import com.example.agentplatform.domain.execution.NodeResult;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Node executor responsible for parsing structured and unstructured documents using Apache Tika.
 */
@Component("doc_parse")
public class DocumentParseNodeExecutor implements NodeExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentParseNodeExecutor.class);
    private final Tika tika = new Tika();

    @Override
    /** {@inheritDoc} */
    public Mono<NodeResult> execute(final NodeExecutionRequest request) {
        return Mono.fromCallable(() -> parseDocument(request));
    }

    /**
     * Parses the configured document using Apache Tika.
     *
     * @param request execution request carrying context and node configuration
     * @return node result with extracted text and metadata
     * @throws Exception if the underlying parser fails or the file cannot be accessed
     */
    private NodeResult parseDocument(final NodeExecutionRequest request) throws Exception {
        final Map<String, Object> config = request.context();
        final Object pathValue = config.get("filePath");
        if (pathValue == null) {
            throw new IllegalArgumentException("filePath not provided");
        }
        final Path path = Path.of(pathValue.toString());
        LOGGER.debug("Parsing document {}", path);
        try (InputStream inputStream = Files.newInputStream(path)) {
            final String text = tika.parseToString(inputStream);
            final Map<String, Object> output = new HashMap<>();
            output.put("documentText", text);
            output.put("documentPath", path.toString());
            return new NodeResult(output, "Parsed " + text.length() + " chars");
        }
    }
}
