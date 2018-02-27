package com.github.framework.common.exception.rpc;

/**
 *
 */
public class NoServiceException extends RuntimeException {
    private static final long serialVersionUID = -1816157068251292353L;

    public NoServiceException(String message) {
        super(message);
    }

    public NoServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NoServiceException(Throwable cause) {
        super(cause);
    }
}
