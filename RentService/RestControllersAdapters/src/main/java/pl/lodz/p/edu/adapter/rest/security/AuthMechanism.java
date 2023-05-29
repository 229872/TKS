package pl.lodz.p.edu.adapter.rest.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.lodz.p.edu.adapter.repository.clients.api.ClientRepository;
import pl.lodz.p.edu.ports.outgoing.SecurityPort;

import java.util.Collections;
import java.util.HashSet;

@RequestScoped
public class AuthMechanism implements HttpAuthenticationMechanism {

    @Inject
    private SecurityPort securityPort;


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response,
                                                HttpMessageContext context) {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.replace("Bearer ", "");
        }

        try {
            if (token == null || !securityPort.validateToken(token)) {
                throw new AuthenticationException();
            }
            String subject = securityPort.getSubject(token);
            return context.notifyContainerAboutLogin(subject,
                    new HashSet<>(Collections.singleton(securityPort.getRole(token))));
        } catch (AuthenticationException e) {
            return context.notifyContainerAboutLogin("anonymous", new HashSet<>(Collections.singleton("ANONYMOUS")));
        }
    }
}
