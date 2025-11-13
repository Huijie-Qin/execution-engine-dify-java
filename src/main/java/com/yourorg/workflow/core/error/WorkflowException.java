package com.yourorg.workflow.core.error;

import lombok.Getter;

/**
 * 运行时异常，用于携带 {@link ErrorCode} 与详细信息，便于 Reactor 流程在错误通道中统一处理。
 */
@Getter
public class WorkflowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    /**
     * 使用指定的错误码与消息创建异常。
     *
     * @param errorCode 错误码，指示错误类别
     * @param message   人类可读的错误描述
     */
    public WorkflowException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 使用指定的错误码、消息与根因创建异常。
     *
     * @param errorCode 错误码，指示错误类别
     * @param message   人类可读的错误描述
     * @param cause     原始异常，便于排障
     */
    public WorkflowException(final ErrorCode errorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
