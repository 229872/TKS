package pl.lodz.p.edu.ports.incoming;

import pl.lodz.p.edu.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.DTO.TokenDTO;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;

public interface AuthenticationService {
    TokenDTO login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws AuthenticationFailureException;
}
