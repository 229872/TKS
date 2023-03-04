package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;
import pl.lodz.p.edu.core.domain.model.security.Token;
import pl.lodz.p.edu.core.domain.model.users.User;
import pl.lodz.p.edu.core.security.JwtUtilities;
import pl.lodz.p.edu.ports.incoming.AuthenticationServicePort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;


@Transactional
@RequestScoped
public class AuthenticationServiceImpl implements AuthenticationServicePort {

    @Inject
    private UserRepositoryPort userRepository;

    @Inject
    JwtUtilities utilities;

    public Token login(String login, String password) throws AuthenticationFailureException {
        User user = getUser(login, password);
        // if password ok then:
        return new Token(utilities.generateToken(user.getLogin(), user.getUserType()));
    }

    public String changePassword(CredentialsNewPassword credentials) throws AuthenticationFailureException {
        User user = getUser(credentials.getLogin(), credentials.getPassword());
        // if old password ok then:
        User updated = userRepository.get(user.getEntityId());
        updated.setPassword(credentials.getNewPassword());
        userRepository.update(updated);
        return null;
    }

    private User getUser(String login, String password) throws AuthenticationFailureException {
        try {
            User user = userRepository.getByLoginPassword(login, password);
            if (!user.isActive()) {
                throw new AuthenticationFailureException("User is inactive");
            }
            return user;
        } catch (NoResultException e) {
            throw new AuthenticationFailureException("User not found");
        }
    }
}
