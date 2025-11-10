package com.example.agentplatform.domain.model;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Persisted record of installed plugins including enablement state and manifest metadata.
 */
@Data
public class PluginRegistryEntry {

    private Long id;
    private String name;
    private String version;
    private String type;
    private Boolean enabled;
    private String manifestJson;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
