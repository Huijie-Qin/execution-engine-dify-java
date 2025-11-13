package com.yourorg.workflow.core.error;

/**
 * 表示工作流执行过程中可能出现的错误类型。
 */
public enum ErrorCode {
    /**
     * 超时错误，通常由于节点执行超过约定时间。
     */
    TIMEOUT,
    /**
     * 输入或上下文校验失败。
     */
    VALIDATION_FAILED,
    /**
     * 底层模型或服务提供方返回的异常。
     */
    PROVIDER_ERROR,
    /**
     * 外部显式取消导致的终止。
     */
    CANCELLED,
    /**
     * 未归类的内部错误。
     */
    INTERNAL
}
