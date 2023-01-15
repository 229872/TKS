package pl.lodz.p.edu.mvc.backingBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;

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
        claims = parseJwt(token);
        try {
            claims = parseJwt(token);
        } catch (Exception e) {
            return httpMessageContext.notifyContainerAboutLogin("GUEST", new HashSet<>(List.of("GUEST")));
        }
        String login = claims.getSubject();
        String userType = claims.get("role", String.class); //Role?
        return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(List.of(userType)));

    }

    public Claims parseJwt(String token) {
        String[] parts = token.split("\\.");
        String parsedToken = parts[0] + '.' + parts[1] + '.';
        return Jwts.parser()
                .parseClaimsJwt(parsedToken)
                .getBody();
    }
}
