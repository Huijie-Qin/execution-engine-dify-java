package com.example.agentplatform.application.service;

import com.example.agentplatform.domain.execution.NodeExecutionRequest;
import com.example.agentplatform.domain.execution.NodeRegistry;
import com.example.agentplatform.domain.execution.WorkflowGraph;
import com.example.agentplatform.domain.model.EdgeDefinition;
import com.example.agentplatform.domain.model.Execution;
import com.example.agentplatform.domain.model.NodeDefinition;
import com.example.agentplatform.domain.model.NodeRun;
import com.example.agentplatform.domain.model.WorkflowVersion;
import com.example.agentplatform.infrastructure.mapper.EdgeDefinitionMapper;
import com.example.agentplatform.infrastructure.mapper.ExecutionMapper;
import com.example.agentplatform.infrastructure.mapper.NodeDefinitionMapper;
import com.example.agentplatform.infrastructure.mapper.NodeRunMapper;
import com.example.agentplatform.infrastructure.mapper.WorkflowVersionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Coordinates workflow executions by delegating to the workflow graph and registered node
 * executors. This service is the transactional boundary for creating execution and node run
 * records.
 */
@Service
@RequiredArgsConstructor
public class ExecutionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionService.class);

    private final WorkflowVersionMapper workflowVersionMapper;
    private final NodeDefinitionMapper nodeDefinitionMapper;
    private final EdgeDefinitionMapper edgeDefinitionMapper;
    private final ExecutionMapper executionMapper;
    private final NodeRunMapper nodeRunMapper;
    private final NodeRegistry nodeRegistry;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Triggers an execution for the provided workflow version identifier.
     *
     * @param workflowVersionId version identifier
     * @param initialContext initial context map
     * @return publisher emitting final context map
     */
    @Transactional(timeout = 60)
    public Mono<Map<String, Object>> execute(final Long workflowVersionId, final Map<String, Object> initialContext) {
        final WorkflowVersion version = workflowVersionMapper
                .findById(workflowVersionId)
                .orElseThrow(() -> new IllegalArgumentException("Workflow version not found"));
        final List<NodeDefinition> nodes = nodeDefinitionMapper.findByWorkflowVersionId(version.getId());
        final List<EdgeDefinition> edges = edgeDefinitionMapper.findByWorkflowVersionId(version.getId());
        final WorkflowGraph graph = WorkflowGraph.build(nodes, edges);

        final Execution execution = new Execution();
        execution.setWorkflowVersionId(version.getId());
        execution.setStatus("RUNNING");
        execution.setContextJson(writeJson(initialContext));
        executionMapper.insert(execution);

        return Mono.fromCallable(() -> runSequential(execution, graph, initialContext))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(resultContext -> {
                    execution.setStatus("SUCCEEDED");
                    execution.setEndedAt(OffsetDateTime.now());
                    execution.setContextJson(writeJson(resultContext));
                    executionMapper.update(execution);
                })
                .doOnError(error -> {
                    execution.setStatus("FAILED");
                    execution.setEndedAt(OffsetDateTime.now());
                    execution.setContextJson(writeJson(initialContext));
                    executionMapper.update(execution);
                });
    }

    /**
     * Sequentially executes the workflow graph starting from the start node.
     *
     * @param execution persisted execution metadata
     * @param graph workflow graph
     * @param initialContext initial context
     * @return aggregated context after running nodes
     * @throws Exception if any node execution fails
     */
    private Map<String, Object> runSequential(
            final Execution execution, final WorkflowGraph graph, final Map<String, Object> initialContext) throws Exception {
        final Map<String, Object> context = new HashMap<>(initialContext);
        NodeDefinition current = graph.getStartNode();
        while (current != null) {
            final NodeRun nodeRun = new NodeRun();
            nodeRun.setExecutionId(execution.getId());
            nodeRun.setNodeId(current.getId());
            nodeRun.setStatus("RUNNING");
            nodeRun.setStartedAt(OffsetDateTime.now());
            nodeRunMapper.insert(nodeRun);
            try {
                final Map<String, Object> nodeContext = parseConfig(current.getConfigJson());
                nodeContext.putAll(context);
                final var result = nodeRegistry
                        .resolve(current.getType())
                        .execute(new NodeExecutionRequest(current, nodeContext))
                        .block();
                if (result != null && result.output() != null) {
                    context.putAll(result.output());
                    nodeRun.setOutputJson(writeJson(result.output()));
                    nodeRun.setLogs(result.logs());
                }
                nodeRun.setStatus("SUCCEEDED");
            } catch (final Exception ex) {
                LOGGER.error("Node execution failed", ex);
                nodeRun.setStatus("FAILED");
                nodeRun.setLogs(ex.getMessage());
                nodeRun.setEndedAt(OffsetDateTime.now());
                nodeRunMapper.update(nodeRun);
                throw ex;
            }
            nodeRun.setEndedAt(OffsetDateTime.now());
            nodeRunMapper.update(nodeRun);
            final List<NodeDefinition> next = graph.nextNodes(current.getId());
            current = next.isEmpty() ? null : next.getFirst();
        }
        return context;
    }

    /**
     * Parses the node configuration JSON to a mutable map.
     *
     * @param configJson raw config
     * @return parsed map
     */
    private Map<String, Object> parseConfig(final String configJson) {
        try {
            if (configJson == null) {
                return new HashMap<>();
            }
            return objectMapper.readValue(configJson, new TypeReference<>() {});
        } catch (final Exception e) {
            throw new IllegalArgumentException("Invalid node config", e);
        }
    }

    /**
     * Serializes context map to JSON string.
     *
     * @param map context map
     * @return serialized JSON
     */
    private String writeJson(final Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to serialize context", e);
        }
    }
}
