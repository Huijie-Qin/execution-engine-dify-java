package com.example.agentplatform.config;

import com.example.agentplatform.domain.execution.NodeExecutor;
import com.example.agentplatform.domain.execution.NodeRegistry;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration that registers all {@link NodeExecutor} beans into the central
 * {@link NodeRegistry} for lookup during workflow execution.
 */
@Configuration
@RequiredArgsConstructor
public class NodeRegistryConfiguration {

    private final NodeRegistry nodeRegistry;
    private final Map<String, NodeExecutor> executors;

    /**
     * Registers discovered executors after dependency injection completes.
     */
    @PostConstruct
    public void registerExecutors() {
        executors.forEach((beanName, executor) -> nodeRegistry.register(beanName, executor));
    }
}
