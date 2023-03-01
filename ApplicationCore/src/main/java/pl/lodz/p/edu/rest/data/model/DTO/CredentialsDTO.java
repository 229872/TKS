package pl.lodz.p.edu.rest.data.model.DTO;

import jakarta.validation.constraints.NotEmpty;

public class CredentialsDTO {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    public CredentialsDTO() {

    }

    public CredentialsDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
