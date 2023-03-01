package pl.lodz.p.edu.rest.exception;

public class AuthenticationFailureException extends Exception {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
