package pl.lodz.p.edu.usersoap.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

@Getter
@Setter
@ToString
public class EmployeeInputDTO extends UserInputDTO {
    @NotBlank(message = "{desk.empty}")
    private String desk;

    public EmployeeInputDTO() {
        super(UserType.EMPLOYEE);
    }

    public EmployeeInputDTO(String login, String password, String desk) {
        super(login, password, UserType.EMPLOYEE);
        this.desk = desk;
    }
}
