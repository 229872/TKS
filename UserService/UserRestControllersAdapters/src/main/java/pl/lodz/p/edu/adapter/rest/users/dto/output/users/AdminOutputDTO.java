package pl.lodz.p.edu.adapter.rest.users.dto.output.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;
import java.util.UUID;

@Getter
@Setter
@ToString
public class AdminOutputDTO extends UserOutputDTO {
    private String favouriteIceCream;

    public AdminOutputDTO(UUID userId, String login, String favouriteIceCream, boolean active) {
        super(userId, login, UserTypeDTO.ADMIN, active);
        this.setActive(active);
        this.favouriteIceCream = favouriteIceCream;
    }

    public AdminOutputDTO() {
        super(UserTypeDTO.ADMIN);
    }
}
