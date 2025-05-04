package io.github.maxmmin.sol.core.crypto.exception;

public class NonceNotFoundException extends Exception {
    public NonceNotFoundException() {
    }

    public NonceNotFoundException(String message) {
        super(message);
    }

    public NonceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonceNotFoundException(Throwable cause) {
        super(cause);
    }

    public NonceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
