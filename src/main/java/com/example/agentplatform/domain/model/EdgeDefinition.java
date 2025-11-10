package com.example.agentplatform.domain.model;

import lombok.Data;

/**
 * Represents a directional edge in the workflow DAG. Conditions determine whether the edge is
 * traversed at runtime.
 */
@Data
public class EdgeDefinition {

    private Long id;
    private Long workflowVersionId;
    private Long fromNodeId;
    private Long toNodeId;
    private String conditionJson;
}
