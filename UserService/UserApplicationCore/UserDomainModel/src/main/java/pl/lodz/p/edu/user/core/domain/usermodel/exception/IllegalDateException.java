package pl.lodz.p.edu.user.core.domain.usermodel.exception;

public class IllegalDateException extends Exception{
    public IllegalDateException() {
    }

    public IllegalDateException(String message) {
        super(message);
    }

    public IllegalDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDateException(Throwable cause) {
        super(cause);
    }
}
