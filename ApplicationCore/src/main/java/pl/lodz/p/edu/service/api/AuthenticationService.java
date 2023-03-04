package pl.lodz.p.edu.service.api;

import pl.lodz.p.edu.core.domain.exception
import pl.lodz.p.edu.core


public interface AuthenticationService {
    Token login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws AuthenticationFailureException;
}
