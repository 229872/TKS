package pl.lodz.p.edu.adapter.rest.exception;

public class RestIllegalModificationException extends Exception {
    public RestIllegalModificationException() {
    }

    public RestIllegalModificationException(String message) {
        super(message);
    }

    public RestIllegalModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestIllegalModificationException(Throwable cause) {
        super(cause);
    }

    public RestIllegalModificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
