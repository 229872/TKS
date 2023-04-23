package pl.lodz.p.edu.adapter.rest.adapter;

import com.nimbusds.jose.JOSEException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.security.credential.CredentialsFromNewPasswordDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.security.token.TokenFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.AuthenticationService;
import pl.lodz.p.edu.adapter.rest.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.adapter.rest.dto.TokenDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;
import pl.lodz.p.edu.core.domain.model.security.Token;
import pl.lodz.p.edu.ports.incoming.AuthenticationServicePort;

import java.text.ParseException;

@ApplicationScoped
public class AuthenticationRestControllerAdapter implements AuthenticationService {

    @Inject
    private AuthenticationServicePort authenticationPort;

    @Inject
    private TokenFromDomainToDTOMapper toDTOMapper;

    @Inject
    private CredentialsFromNewPasswordDTOToDomainMapper credentialsFromNewPasswordDTOToDomainMapper;

    @Override
    public TokenDTO login(String login, String password) throws RestAuthenticationFailureException {
        try {
            Token token = authenticationPort.login(login, password);
            return toDTOMapper.convertToDTO(token);
        } catch (AuthenticationFailureException e) {
            throw new RestAuthenticationFailureException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void changePassword(CredentialsNewPasswordDTO credentials) throws RestAuthenticationFailureException {
        try {
            CredentialsNewPassword cnp = credentialsFromNewPasswordDTOToDomainMapper.convertToDomainModel(credentials);
            authenticationPort.changePassword(cnp);
        } catch (AuthenticationFailureException e) {
            throw new RestAuthenticationFailureException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void verifySingedLogin(String ifMatch, String json) throws RestAuthenticationFailureException {
        try {
            authenticationPort.verifySingedLogin(ifMatch, json);
        } catch (ParseException | AuthenticationFailureException | JOSEException e) {
            throw new RestAuthenticationFailureException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String signLogin(String login) throws JOSEException {
        return authenticationPort.signLogin(login);
    }
}
