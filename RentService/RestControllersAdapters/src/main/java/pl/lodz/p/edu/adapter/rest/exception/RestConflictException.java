package pl.lodz.p.edu.adapter.rest.exception;

public class RestConflictException extends Exception {
    public RestConflictException() {
    }

    public RestConflictException(String message) {
        super(message);
    }

    public RestConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestConflictException(Throwable cause) {
        super(cause);
    }

    public RestConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
