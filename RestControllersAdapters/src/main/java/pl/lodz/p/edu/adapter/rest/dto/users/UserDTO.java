package pl.lodz.p.edu.adapter.rest.dto.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public abstract class UserDTO {

    @NotEmpty(message = "Login can't be empty")
    private String login;

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




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
