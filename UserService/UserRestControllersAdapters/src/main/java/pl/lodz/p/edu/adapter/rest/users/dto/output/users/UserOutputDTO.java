package pl.lodz.p.edu.adapter.rest.users.dto.output.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;

import java.util.UUID;

@AllArgsConstructor
@Data
public abstract class UserOutputDTO {
    private UUID userId;
    private String login;
    private UserTypeDTO userType;
    private boolean active;

    public UserOutputDTO(UserTypeDTO userType) {
        this.userType = userType;
    }
}
