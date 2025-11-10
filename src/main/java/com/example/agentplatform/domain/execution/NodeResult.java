package com.example.agentplatform.domain.execution;

import java.util.Map;

/**
 * Result produced by a node execution containing structured output and logs.
 */
public record NodeResult(Map<String, Object> output, String logs) {
}
