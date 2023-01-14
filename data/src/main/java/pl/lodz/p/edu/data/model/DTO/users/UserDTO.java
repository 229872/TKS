package pl.lodz.p.edu.data.model.DTO.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.data.model.users.User;

public class UserDTO {

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

    public UserDTO() {}

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
