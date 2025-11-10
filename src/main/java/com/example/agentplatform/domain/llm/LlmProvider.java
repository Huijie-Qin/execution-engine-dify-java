package com.example.agentplatform.domain.llm;

import reactor.core.publisher.Flux;

/**
 * Contract for interacting with language model providers.
 */
public interface LlmProvider {

    /**
     * Executes a chat completion request.
     *
     * @param request payload
     * @return response text
     */
    String chat(LlmRequest request);

    /**
     * Streams chat tokens for SSE output.
     *
     * @param request payload
     * @return stream of tokens
     */
    Flux<String> streamChat(LlmRequest request);
}
