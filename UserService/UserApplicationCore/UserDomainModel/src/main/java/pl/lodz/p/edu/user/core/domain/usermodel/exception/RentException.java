package pl.lodz.p.edu.user.core.domain.usermodel.exception;

public class RentException extends Exception {
    public RentException(String message) {
        super(message);
    }

    public RentException(Throwable cause) {
        super(cause);
    }
}
