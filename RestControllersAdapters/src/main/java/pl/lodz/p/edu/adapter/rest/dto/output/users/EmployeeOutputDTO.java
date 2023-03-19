package pl.lodz.p.edu.adapter.rest.dto.output.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;

import java.util.UUID;

@Getter
@Setter
@ToString
public class EmployeeOutputDTO extends UserOutputDTO {

    private String desk;
    public EmployeeOutputDTO(UUID userId, String login, String desk) {
        super(userId, login, UserTypeDTO.EMPLOYEE);
        this.desk = desk;
    }

    public EmployeeOutputDTO() {
        super(UserTypeDTO.EMPLOYEE);
    }
}
