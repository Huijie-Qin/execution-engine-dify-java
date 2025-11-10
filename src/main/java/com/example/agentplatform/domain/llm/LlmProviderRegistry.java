package com.example.agentplatform.domain.llm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Registry for LLM providers keyed by provider identifier.
 */
@Component
public class LlmProviderRegistry {

    private final Map<String, LlmProvider> providers = new ConcurrentHashMap<>();

    /**
     * Registers a provider under its identifier.
     *
     * @param id provider id
     * @param provider provider implementation
     */
    public void register(final String id, final LlmProvider provider) {
        providers.put(id, provider);
    }

    /**
     * Resolves a provider by identifier.
     *
     * @param id provider id
     * @return provider implementation
     */
    public LlmProvider resolve(final String id) {
        final LlmProvider provider = providers.get(id);
        if (provider == null) {
            throw new IllegalArgumentException("Unknown LLM provider: " + id);
        }
        return provider;
    }
}
