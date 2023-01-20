package pl.lodz.p.edu.rest.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;

@ApplicationScoped
public class JwtUtilities {

    private String secret = "MHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLN";

    private int exp_time = 2137000;

    public Jws<Claims> parseJWT(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
    }

    public String generateToken(String subject, String userType) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .claim("userType", userType)
                .setExpiration(new Date(System.currentTimeMillis() + exp_time))
                .signWith(SignatureAlgorithm.HS256, secret) //FIXME ALGORITHM
                .compact();

    }
}
