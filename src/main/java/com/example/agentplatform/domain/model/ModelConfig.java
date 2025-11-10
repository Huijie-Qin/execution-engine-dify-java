package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Stores configuration for external LLM providers. API keys are referenced by alias to avoid
 * storing secrets directly in the database.
 */
@Data
public class ModelConfig {

    private Long id;
    private String provider;
    private String modelName;
    private String baseUrl;
    private String apiKeyAlias;
    private Integer timeoutMs;
    private Integer maxTokens;
    private String extraJson;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
