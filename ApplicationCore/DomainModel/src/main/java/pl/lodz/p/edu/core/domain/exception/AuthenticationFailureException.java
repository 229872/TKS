package pl.lodz.p.edu.core.domain.exception;

public class AuthenticationFailureException extends Exception {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
