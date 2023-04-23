package pl.lodz.p.edu.user.core.domain.usermodel.exception;

public class ObjectNotValidException extends Exception {
    public ObjectNotValidException(String message) {
        super(message);
    }

    public ObjectNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
