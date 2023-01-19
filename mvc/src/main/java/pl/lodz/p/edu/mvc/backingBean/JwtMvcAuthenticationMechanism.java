package pl.lodz.p.edu.mvc.backingBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@AutoApplySession
public class JwtMvcAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private JwtSessionBean sessionBean;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {

        String token = sessionBean.getJwtToken();
        if (token.isEmpty() || token.isBlank()) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }
        Claims claims;
        try {
            claims = parseJwt(token);
        } catch (Exception e) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }
        String login = claims.getSubject();
        String userType = claims.get("userType", String.class); //Role?
        httpMessageContext.forward(userType);
        Set<String> roles = new HashSet<>();
        roles.add(userType);
        return httpMessageContext.notifyContainerAboutLogin(login, roles);
    }

    public Claims parseJwt(String token) {
        String[] parts = token.split("\\.");
        String parsedToken = parts[0] + '.' + parts[1] + '.';
        return Jwts.parser()
                .parseClaimsJwt(parsedToken)
                .getBody();
    }
}
