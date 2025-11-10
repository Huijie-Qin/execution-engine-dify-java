package com.example.agentplatform.infrastructure.llm;

import org.springframework.stereotype.Component;

/**
 * Resolves API key aliases to environment variables.
 */
@Component
public class ApiKeyResolver {

    /**
     * Resolves alias into real API key using uppercase underscore environment variable naming.
     *
     * @param alias alias stored in the database
     * @return resolved API key
     */
    public String resolve(final String alias) {
        final String envKey = alias.toUpperCase().replace('-', '_') + "_API_KEY";
        final String value = System.getenv(envKey);
        if (value == null) {
            throw new IllegalStateException("API key not configured for alias: " + alias);
        }
        return value;
    }
}
