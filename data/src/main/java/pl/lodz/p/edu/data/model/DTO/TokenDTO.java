package pl.lodz.p.edu.data.model.DTO;

public class TokenDTO {
    String jwt;

    public TokenDTO(String jwt) {
        this.jwt = jwt;
    }

    public TokenDTO() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
