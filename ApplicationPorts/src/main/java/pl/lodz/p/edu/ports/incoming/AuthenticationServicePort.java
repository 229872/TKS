package pl.lodz.p.edu.ports.incoming;


import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;
import pl.lodz.p.edu.core.domain.model.security.Token;

public interface AuthenticationServicePort {
    Token login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPassword credentials) throws AuthenticationFailureException;
}
