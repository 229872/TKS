package pl.lodz.p.edu.ports.incoming;


import com.nimbusds.jose.JOSEException;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;
import pl.lodz.p.edu.core.domain.model.security.Token;

import java.text.ParseException;

public interface AuthenticationServicePort {
    Token login(String login, String password) throws AuthenticationFailureException;

    void changePassword(CredentialsNewPassword credentials) throws AuthenticationFailureException;

    String signLogin(String login) throws JOSEException;

    void verifySingedLogin(String ifMatch, String json) throws ParseException,
            AuthenticationFailureException, JOSEException;
}
