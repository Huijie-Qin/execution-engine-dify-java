package com.example.agentplatform.domain.llm;

import java.util.List;

/**
 * Immutable request describing messages sent to the LLM.
 */
public record LlmRequest(String model, List<String> messages, int maxTokens) {
}
