package pl.lodz.p.edu.adapter.rest.exception;

public class ObjectNotFoundRestException extends Exception {
    public ObjectNotFoundRestException() {
    }

    public ObjectNotFoundRestException(String message) {
        super(message);
    }

    public ObjectNotFoundRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundRestException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundRestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
