package com.mms.exception;

public final class InvalidOldPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(final String message) {
        super(message);
    }

    public InvalidOldPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
