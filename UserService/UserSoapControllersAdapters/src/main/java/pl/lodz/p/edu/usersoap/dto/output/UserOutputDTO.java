package pl.lodz.p.edu.usersoap.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

import java.util.UUID;

@AllArgsConstructor
@Data
public abstract class UserOutputDTO {
    private UUID userId;
    private String login;
    private UserType userType;
    private boolean active;

    public UserOutputDTO(UserType userType) {
        this.userType = userType;
    }
}
