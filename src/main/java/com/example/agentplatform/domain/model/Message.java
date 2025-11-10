package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Conversation message persisted for memory. Includes tracing metadata for audit and streaming.
 */
@Data
public class Message {

    private Long id;
    private Long conversationId;
    private String role;
    private String content;
    private Integer tokens;
    private String traceId;
    private OffsetDateTime createdAt;
}
