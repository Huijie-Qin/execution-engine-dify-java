package com.example.agentplatform.domain.execution;

import reactor.core.publisher.Mono;

/**
 * Defines the contract for executing a workflow node. Implementations may run synchronous or
 * asynchronous workloads but must respect timeouts, retries, and provide deterministic outputs.
 */
public interface NodeExecutor {

    /**
     * Executes the node with the given request.
     *
     * @param request execution context
     * @return publisher emitting {@link NodeResult}
     */
    Mono<NodeResult> execute(NodeExecutionRequest request);

    /**
     * @return maximum allowed execution duration in milliseconds
     */
    default long timeoutMs() {
        return 30000L;
    }

    /**
     * @return maximum number of retries for transient failures
     */
    default int maxRetries() {
        return 0;
    }
}
