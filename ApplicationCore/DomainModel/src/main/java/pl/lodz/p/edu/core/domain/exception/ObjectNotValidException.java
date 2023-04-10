package pl.lodz.p.edu.core.domain.exception;

public class ObjectNotValidException extends Exception {
    public ObjectNotValidException(String message) {
        super(message);
    }

    public ObjectNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
