package pl.lodz.p.edu.adapter.rest.users.dto.input.users;

import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeInputDTO extends UserInputDTO {
    @NotBlank(message = "{desk.empty}")
    private String desk;

    public EmployeeInputDTO() {
        super(UserTypeDTO.EMPLOYEE);
    }

    public EmployeeInputDTO(String login, String password, String desk) {
        super(login, password, UserTypeDTO.EMPLOYEE);
        this.desk = desk;
    }
}
