package com.example.agentplatform.domain.model;

import lombok.Data;

/**
 * Node definition stored in the database as part of a workflow version. Each node references a
 * plugin or built-in executor via its type identifier.
 */
@Data
public class NodeDefinition {

    private Long id;
    private Long workflowVersionId;
    private String type;
    private String configJson;
    private String inSchema;
    private String outSchema;
    private Integer positionX;
    private Integer positionY;
}
