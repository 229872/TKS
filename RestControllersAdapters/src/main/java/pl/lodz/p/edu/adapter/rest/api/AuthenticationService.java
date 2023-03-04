package pl.lodz.p.edu.adapter.rest.api;

import com.nimbusds.jose.JOSEException;
import pl.lodz.p.edu.adapter.rest.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.adapter.rest.dto.TokenDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;

import java.text.ParseException;

public interface AuthenticationService {
    TokenDTO login(String login, String password) throws RestAuthenticationFailureException;

    String changePassword(CredentialsNewPasswordDTO credentials) throws RestAuthenticationFailureException;

    void verifySingedLogin(String ifMatch, String json) throws ParseException,
            RestAuthenticationFailureException, JOSEException;

    String signLogin(String login) throws JOSEException;
}
