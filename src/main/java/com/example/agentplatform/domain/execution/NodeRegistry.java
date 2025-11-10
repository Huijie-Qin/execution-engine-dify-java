package com.example.agentplatform.domain.execution;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Registry mapping node type identifiers to executor implementations. Supports runtime plugin
 * registration.
 */
@Component
public class NodeRegistry {

    private final Map<String, NodeExecutor> executors = new ConcurrentHashMap<>();

    /**
     * Registers a node executor.
     *
     * @param type node type identifier
     * @param executor executor instance
     */
    public void register(final String type, final NodeExecutor executor) {
        executors.put(type, executor);
    }

    /**
     * Resolves a node executor by type.
     *
     * @param type node type identifier
     * @return executor instance
     */
    public NodeExecutor resolve(final String type) {
        final NodeExecutor executor = executors.get(type);
        if (executor == null) {
            throw new IllegalArgumentException("Unknown node type: " + type);
        }
        return executor;
    }
}
