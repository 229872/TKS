package pl.lodz.p.edu.adapter.rest.dto.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public abstract class UserDTO {

    @NotNull
    @NotEmpty
    private String login;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    private UserTypeDTO userType;

    public UserDTO(UserTypeDTO userType) {
        this.userType = userType;
    }

    public UserDTO(String login, String password, UserTypeDTO userType) {
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserTypeDTO getUserType() {
        return userType;
    }
}
