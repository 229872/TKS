package pl.lodz.p.edu.adapter.rest.dto.output.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AdminOutputDTO extends UserOutputDTO {
    private String favouriteIceCream;

    public AdminOutputDTO(UUID userId, String login, boolean active) {
        super(userId, login, UserTypeDTO.ADMIN, active);
        this.setActive(active);
    }

    public AdminOutputDTO() {
        super(UserTypeDTO.ADMIN);
    }
}
