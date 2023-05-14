package pl.lodz.p.edu.adapter.rest.users.dto.input.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;

@AllArgsConstructor
@Data
public abstract class UserInputDTO {

    @NotBlank(message = "{login.empty}")
    private String login;

    @NotBlank(message = "{password.empty}")
    @Size(min = 8, message = "{password.size}")
    private String password;

    @Getter
    private UserTypeDTO userType;

    public UserInputDTO(UserTypeDTO userType) {
        this.userType = userType;
    }
}
