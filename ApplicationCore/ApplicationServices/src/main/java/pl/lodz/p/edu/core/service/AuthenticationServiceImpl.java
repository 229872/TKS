package pl.lodz.p.edu.core.service;

import com.nimbusds.jose.JOSEException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;
import pl.lodz.p.edu.core.domain.model.security.Token;
import pl.lodz.p.edu.core.domain.model.users.User;
import pl.lodz.p.edu.core.security.JwtUtilities;
import pl.lodz.p.edu.ports.incoming.AuthenticationServicePort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.text.ParseException;


@Transactional
@RequestScoped
public class AuthenticationServiceImpl implements AuthenticationServicePort {

    @Inject
    private UserRepositoryPort userRepository;

    @Inject
    JwtUtilities utilities;

    @Override
    public Token login(String login, String password) throws AuthenticationFailureException {
        User user = checkIsUserActive(login);

        if (user.getPassword().equals(password)) {
            return new Token(utilities.generateToken(user.getLogin(), user.getUserType().toString()));
        } else {
            throw new AuthenticationFailureException("Wrong password");
        }
    }

    @Override
    public void changePassword(CredentialsNewPassword credentials) throws AuthenticationFailureException {
        User user = checkIsUserActive(credentials.getLogin());
        // if old password ok then:
        if (user.getPassword().equals(credentials.getPassword())) {
            user.setPassword(credentials.getNewPassword());
            userRepository.update(user);
        } else {
            throw new AuthenticationFailureException("Old password is wrong");
        }
    }

    private User checkIsUserActive(String login) throws AuthenticationFailureException {
        try {
            User user = userRepository.getByLogin(login);
            if (!user.isActive()) {
                throw new AuthenticationFailureException("User is inactive");
            }
            return user;
        } catch (ObjectNotFoundServiceException e) {
            throw new AuthenticationFailureException("User not found");
        }
    }

    public void verifySingedLogin(String ifMatch, String json) throws ParseException,
            AuthenticationFailureException, JOSEException {
        utilities.verifySingedLogin(ifMatch, json);
    }

    public String signLogin(String login) throws JOSEException {
        return utilities.signLogin(login);
    }
}