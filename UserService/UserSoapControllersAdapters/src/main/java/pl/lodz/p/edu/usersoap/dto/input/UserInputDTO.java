package pl.lodz.p.edu.usersoap.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

@AllArgsConstructor
@Data
public abstract class UserInputDTO {

    @NotBlank(message = "{login.empty}")
    private String login;

    @NotBlank(message = "{password.empty}")
    @Size(min = 8, message = "{password.size}")
    private String password;

    @Getter
    private UserType userType;

    public UserInputDTO(UserType userType) {
        this.userType = userType;
    }
}
