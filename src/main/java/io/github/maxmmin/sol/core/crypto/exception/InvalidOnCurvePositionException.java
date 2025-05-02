package io.github.maxmmin.sol.core.crypto.exception;

public class InvalidOnCurvePositionException extends Exception {
    public InvalidOnCurvePositionException() {
    }

    public InvalidOnCurvePositionException(String message) {
        super(message);
    }

    public InvalidOnCurvePositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOnCurvePositionException(Throwable cause) {
        super(cause);
    }

    public InvalidOnCurvePositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
