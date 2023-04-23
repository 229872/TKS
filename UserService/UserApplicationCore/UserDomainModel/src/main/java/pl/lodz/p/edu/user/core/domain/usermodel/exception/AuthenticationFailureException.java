package pl.lodz.p.edu.user.core.domain.usermodel.exception;

public class AuthenticationFailureException extends Exception {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
