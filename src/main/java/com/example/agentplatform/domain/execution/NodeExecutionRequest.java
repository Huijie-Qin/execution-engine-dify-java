package com.example.agentplatform.domain.execution;

import com.example.agentplatform.domain.model.NodeDefinition;
import java.util.Map;

/**
 * Immutable execution input containing the current node definition and workflow-scoped context.
 */
public record NodeExecutionRequest(NodeDefinition node, Map<String, Object> context) {
}
