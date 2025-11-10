package com.example.agentplatform.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for creating workflows.
 */
public record CreateWorkflowRequest(
        @NotBlank @Schema(description = "Workflow name") String name,
        @NotBlank @Schema(description = "JSON graph definition") String graphJson,
        @NotBlank @Schema(description = "JSON variable defaults") String variablesJson,
        @Schema(description = "Workflow description") String description) {}
