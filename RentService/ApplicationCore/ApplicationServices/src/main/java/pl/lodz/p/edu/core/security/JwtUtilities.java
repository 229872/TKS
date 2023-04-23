package pl.lodz.p.edu.core.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;

import java.text.ParseException;
import java.util.Date;

@ApplicationScoped
public class JwtUtilities {

    private final String secret = "MHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLNMHDVHZCNLN";

    private final int exp_time = 2137000;

    public Jws<Claims> parseJWT(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
    }

    public String generateToken(String subject, String userType) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .claim("userType", userType)
                .setExpiration(new Date(System.currentTimeMillis() + exp_time))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public String signLogin(String login) throws JOSEException {
        JWSSigner jwsSigner = new MACSigner(secret);

        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(login));

        jwsObject.sign(jwsSigner);

        return jwsObject.serialize();
    }

    public void verifySingedLogin(String ifMatchHeader, String jsonLogin) throws ParseException,
            JOSEException, AuthenticationFailureException {

        JWSObject jws = JWSObject.parse(ifMatchHeader);

        JWSVerifier jwsVerifier = new MACVerifier(secret);

        if (!jws.verify(jwsVerifier)) {
            throw new AuthenticationFailureException("Not verified");
        }

        String signedLogin = signLogin(jsonLogin);
        if (!ifMatchHeader.equals(signedLogin)) {
            throw new AuthenticationFailureException("Not verified");
        }
    }
}
