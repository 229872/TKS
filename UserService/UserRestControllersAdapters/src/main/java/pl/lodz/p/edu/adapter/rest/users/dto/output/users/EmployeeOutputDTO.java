package pl.lodz.p.edu.adapter.rest.users.dto.output.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;

import java.util.UUID;

@Getter
@Setter
@ToString
public class EmployeeOutputDTO extends UserOutputDTO {

    private String desk;
    public EmployeeOutputDTO(UUID userId, String login, String desk, boolean active) {
        super(userId, login, UserTypeDTO.EMPLOYEE, active);
        this.setActive(active);
        this.desk = desk;
    }

    public EmployeeOutputDTO() {
        super(UserTypeDTO.EMPLOYEE);
    }
}
