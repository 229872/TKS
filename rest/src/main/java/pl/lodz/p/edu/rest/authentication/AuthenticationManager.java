package pl.lodz.p.edu.rest.authentication;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

@RequestScoped
public class AuthenticationManager {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtUtilities utilities;

    public String login(String login, String password) throws AuthenticationFailureException {
        User user;
        try {
            user = userRepository.getByOnlyLogin(login);
        } catch (NoResultException e) {
            throw new AuthenticationFailureException("User not found");
        }


        if (!user.isActive()) {
            throw new AuthenticationFailureException("User is inactive");

        }

        // todo?
        return utilities.generateToken(user.getLogin(), user.getRole().toString());

    }
}
