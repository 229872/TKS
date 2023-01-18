package pl.lodz.p.edu.rest.authentication;

import io.jsonwebtoken.Claims;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

import java.security.Principal;
import java.util.*;

@ApplicationScoped
public class JwtRestAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    JwtUtilities jwtUtilities;

    @Inject
    UserRepository userRepository;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        String authorizationHeader = httpServletRequest.getHeader("AUTHORIZATION");
        String token;
        if (authorizationHeader == null || !authorizationHeader.startsWith("BEARER")) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }
        String tokenToParse = authorizationHeader.substring("BEARER".length());
        Claims claims;

        try {
            claims = jwtUtilities.parseJWT(tokenToParse).getBody();
        } catch (Exception e) { //idk too broad?
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }

        String userLogin = claims.getSubject();

        User dbUser = userRepository.getByOnlyLogin(userLogin); //TODO REMEMBER
//        throw new RuntimeException(claims.toString());
        if (dbUser == null) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }                                                   // Not sure about principal
        return httpMessageContext.notifyContainerAboutLogin(dbUser.getLogin(), new HashSet<>(List.of(claims.get("userType", String.class))));


    }
}
