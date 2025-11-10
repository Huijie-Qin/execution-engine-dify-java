package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Represents an immutable snapshot of a workflow definition including its graph and variable
 * definitions. Versions are executed to ensure reproducible results.
 */
@Data
public class WorkflowVersion {

    private Long id;
    private Long workflowId;
    private String graphJson;
    private String variablesJson;
    private String status;
    private OffsetDateTime createdAt;
}
