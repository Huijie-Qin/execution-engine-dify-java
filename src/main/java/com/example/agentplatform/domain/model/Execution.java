package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Represents a single run of a workflow version. Stores aggregated context and lifecycle
 * timestamps for auditing and replay purposes.
 */
@Data
public class Execution {

    private Long id;
    private Long workflowVersionId;
    private String status;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
    private String contextJson;
}
