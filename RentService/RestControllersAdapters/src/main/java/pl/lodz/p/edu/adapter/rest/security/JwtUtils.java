package pl.lodz.p.edu.adapter.rest.security;

import io.jsonwebtoken.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.ports.outgoing.SecurityPort;

import java.util.Date;

@ApplicationScoped
public class JwtUtils implements SecurityPort {

    @Inject
    @ConfigProperty(name = "token.expiration.time")
    private int exp_time;

    @Inject
    @ConfigProperty(name = "token.secret")
    private String secret;

    @Override
    public String generateToken(String subject, String role) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + exp_time))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseJWT(token);
            return true;
        } catch(JwtException e) {
            return false;
        }
    }

    @Override
    public String getSubject(String token) {
        try {
            return parseJWT(token).getBody().getSubject();
        } catch(JwtException e) {
            return "anonymous";
        }
    }

    @Override
    public String getRole(String token) {
        try {
            return parseJWT(token).getBody().get("role", String.class);
        } catch(JwtException e) {
            return "ANONYMOUS";
        }
    }

    private Jws<Claims> parseJWT(String jwt) throws JwtException {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt);
        } catch (SignatureException e) {
            throw new JwtException("Cannot parse token");
        }
    }
}