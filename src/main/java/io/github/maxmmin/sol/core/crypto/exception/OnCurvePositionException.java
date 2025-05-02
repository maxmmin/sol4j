package io.github.maxmmin.sol.core.crypto.exception;

public class OnCurvePositionException extends Exception {
    public OnCurvePositionException() {
    }

    public OnCurvePositionException(String message) {
        super(message);
    }

    public OnCurvePositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnCurvePositionException(Throwable cause) {
        super(cause);
    }

    public OnCurvePositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
