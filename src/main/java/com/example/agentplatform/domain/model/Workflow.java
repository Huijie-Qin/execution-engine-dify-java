package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Represents a workflow definition that can be published and executed. Each workflow has a
 * human-readable name and status that determines whether it can be executed.
 */
@Data
public class Workflow {

    private Long id;
    private String name;
    private String status;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
