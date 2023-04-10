package pl.lodz.p.edu.adapter.rest.exception;

public class RestIllegalDateException extends Exception {

    public RestIllegalDateException() {
    }

    public RestIllegalDateException(String message) {
        super(message);
    }

    public RestIllegalDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestIllegalDateException(Throwable cause) {
        super(cause);
    }
}
