package pl.lodz.p.edu.rest.managers.api;

import pl.lodz.p.edu.data.model.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.data.model.DTO.TokenDTO;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;

public interface AuthenticationManager {
    TokenDTO login(String login, String password) throws AuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws AuthenticationFailureException;
}
