package pl.lodz.p.edu.core.userdomain.service;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.AuthenticationFailureException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Admin;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Employee;
import pl.lodz.p.edu.user.core.domain.usermodel.users.User;
import pl.lodz.p.edu.userports.incoming.AuthenticationServicePort;
import pl.lodz.p.edu.userports.outgoing.SecurityPort;
import pl.lodz.p.edu.userports.outgoing.UserRepositoryPort;

import java.util.Objects;

@RequestScoped
public class AuthenticationServicePortImpl implements AuthenticationServicePort {

    @Inject
    private UserRepositoryPort userRepository;

    @Inject
    private SecurityPort securityPort;


    @Override
    public String login(String login, String password) throws ObjectNotFoundServiceException, AuthenticationFailureException {
        User user = userRepository.getByLogin(login);

        if (!Objects.equals(user.getPassword(), password)) {
            throw new AuthenticationFailureException("Invalid password");
        }

        if (!user.isActive()) {
            throw new AuthenticationFailureException("User not active");
        }

        String role = "CLIENT";
        if(user instanceof Employee) {
            role = "EMPLOYEE";
        } else if(user instanceof Admin) {
            role = "ADMIN";
        }

        return securityPort.generateToken(user.getLogin(), role);
    }
}
