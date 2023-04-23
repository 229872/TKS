package pl.lodz.p.edu.usersoap.dto.output;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

import java.util.UUID;

@Getter
@Setter
@ToString
public class EmployeeOutputDTO extends UserOutputDTO {

    private String desk;
    public EmployeeOutputDTO(UUID userId, String login, String desk, boolean active) {
        super(userId, login, UserType.EMPLOYEE, active);
        this.setActive(active);
        this.desk = desk;
    }

    public EmployeeOutputDTO() {
        super(UserType.EMPLOYEE);
    }
}
