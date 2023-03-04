package pl.lodz.p.edu.core.domain.model.security;

public class Token {
    String jwt;

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public Token() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
