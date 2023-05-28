package pl.lodz.p.edu.adapter.rest.users.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.User;
import pl.lodz.p.edu.userports.outgoing.SecurityPort;
import pl.lodz.p.edu.userports.outgoing.UserRepositoryPort;

import java.util.Collections;
import java.util.HashSet;

@RequestScoped
public class AuthMechanism implements HttpAuthenticationMechanism {

    @Inject
    private SecurityPort securityPort;

    @Inject
    private UserRepositoryPort userRepositoryPort;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response,
                                                HttpMessageContext context) throws AuthenticationException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.replace("Bearer ", "");
        }

        String subject = securityPort.getSubject(token);
        try {
            if (token == null || !securityPort.validateToken(token)) {
                throw new AuthenticationException();
            }
            User user = userRepositoryPort.getByLogin(subject);
            return context.notifyContainerAboutLogin(user.getLogin(),
                    new HashSet<>(Collections.singleton(securityPort.getRole(token))));
        } catch (AuthenticationException | ObjectNotFoundServiceException e) {
            return context.notifyContainerAboutLogin("anonymous", new HashSet<>(Collections.singleton("ANONYMOUS")));
        }
    }
}
