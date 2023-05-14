package pl.lodz.p.edu.adapter.rest.users.exception;

public class RestBusinessLogicInterruptException extends Exception {
    public RestBusinessLogicInterruptException() {
    }

    public RestBusinessLogicInterruptException(String message) {
        super(message);
    }

    public RestBusinessLogicInterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestBusinessLogicInterruptException(Throwable cause) {
        super(cause);
    }

    public RestBusinessLogicInterruptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
