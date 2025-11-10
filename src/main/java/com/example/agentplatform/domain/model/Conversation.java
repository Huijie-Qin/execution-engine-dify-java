package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Represents a persisted conversation bound to a workflow or version. The memory policy controls
 * how prior messages are recalled for subsequent executions.
 */
@Data
public class Conversation {

    private Long id;
    private Long workflowId;
    private Long workflowVersionId;
    private String memoryPolicy;
    private OffsetDateTime createdAt;
}
