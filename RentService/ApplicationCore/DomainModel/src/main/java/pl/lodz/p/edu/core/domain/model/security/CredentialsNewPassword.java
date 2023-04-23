package pl.lodz.p.edu.core.domain.model.security;


public class CredentialsNewPassword {

    private String login;

    private String password;

    private String newPassword;

    public CredentialsNewPassword(String login, String password, String newPassword) {
        this.login = login;
        this.password = password;
        this.newPassword = newPassword;
    }

    public CredentialsNewPassword() {
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
