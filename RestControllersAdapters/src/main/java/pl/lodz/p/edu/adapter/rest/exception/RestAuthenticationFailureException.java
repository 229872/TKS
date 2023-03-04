package pl.lodz.p.edu.adapter.rest.exception;

public class RestAuthenticationFailureException extends Exception {
    public RestAuthenticationFailureException() {
    }

    public RestAuthenticationFailureException(String message) {
        super(message);
    }

    public RestAuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestAuthenticationFailureException(Throwable cause) {
        super(cause);
    }

    public RestAuthenticationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
