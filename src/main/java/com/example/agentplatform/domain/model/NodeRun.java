package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Stores runtime state for each node executed as part of an execution. Includes logs and outputs
 * that can be streamed to clients via SSE.
 */
@Data
public class NodeRun {

    private Long id;
    private Long executionId;
    private Long nodeId;
    private String status;
    private String logs;
    private String outputJson;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;
}
