package pl.lodz.p.edu.adapter.rest.dto;

import jakarta.validation.constraints.NotEmpty;

public class CredentialsNewPasswordDTO {
    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    @NotEmpty
    private String newPassword;

    public CredentialsNewPasswordDTO(String login, String password, String newPassword) {
        this.login = login;
        this.password = password;
        this.newPassword = newPassword;
    }

    public CredentialsNewPasswordDTO() {
        this.password = "";
        this.newPassword = "";
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
