package com.nikialeksey.nullfree;

public class NullfreeException extends Exception {
    public NullfreeException(String message) {
        super(message);
    }

    public NullfreeException(String message, Throwable cause) {
        super(message, cause);
    }
}
