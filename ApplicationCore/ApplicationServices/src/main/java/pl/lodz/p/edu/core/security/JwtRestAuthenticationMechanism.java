package pl.lodz.p.edu.core.security;

import io.jsonwebtoken.Claims;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.lodz.p.edu.core.domain.model.users.User;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class JwtRestAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private JwtUtilities jwtUtilities;

    @Inject
    private UserRepositoryPort userRepository;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        String authorizationHeader = httpServletRequest.getHeader("AUTHORIZATION");
        if(authorizationHeader == null) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }
        String bearer = authorizationHeader.substring(0, 6);
        bearer = bearer.toUpperCase();
        if (!bearer.equals("BEARER")) {
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
        String userType = claims.get("userType", String.class);
        System.out.println(userType);
//        User dbUser = userRepository.getByLogin(userType, userLogin); //TODO Kinda bad but whatever
        //Return object that counts as a null???
        User dbUser = userRepository.getByOnlyLogin(userLogin);

        if (dbUser == null) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }                                                   // Not sure about principal
        return httpMessageContext.notifyContainerAboutLogin(dbUser.getLogin(), Collections.singleton(userType));

    }
}
