package pl.lodz.p.edu.service.api;

import pl.lodz.p.edu.exception.AuthenticationFailureException;
import pl.lodz.p.edu.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.DTO.TokenDTO;

public interface AuthenticationService {
    TokenDTO login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws AuthenticationFailureException;
}
