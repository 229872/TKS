package pl.lodz.p.edu.adapter.rest.exception;

public class RestObjectNotValidException extends Exception {
    public RestObjectNotValidException() {
    }

    public RestObjectNotValidException(String message) {
        super(message);
    }

    public RestObjectNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestObjectNotValidException(Throwable cause) {
        super(cause);
    }

    public RestObjectNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
