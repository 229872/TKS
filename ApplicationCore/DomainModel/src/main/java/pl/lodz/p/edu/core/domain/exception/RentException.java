package pl.lodz.p.edu.core.domain.exception;

public class RentException extends Exception {
    public RentException(String message) {
        super(message);
    }

    public RentException(Throwable cause) {
        super(cause);
    }
}
