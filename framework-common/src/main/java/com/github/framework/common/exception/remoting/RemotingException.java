package com.github.framework.common.exception.remoting;

/**
 *
 */
public class RemotingException extends RuntimeException {
    private static final long serialVersionUID = -2088332730795690468L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
