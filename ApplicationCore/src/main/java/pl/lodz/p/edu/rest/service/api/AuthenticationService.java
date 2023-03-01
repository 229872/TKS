package pl.lodz.p.edu.rest.service.api;

import pl.lodz.p.edu.rest.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.rest.DTO.TokenDTO;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;

public interface AuthenticationService {
    TokenDTO login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws AuthenticationFailureException;
}
