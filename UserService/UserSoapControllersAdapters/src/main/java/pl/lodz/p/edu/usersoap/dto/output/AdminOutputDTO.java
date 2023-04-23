package pl.lodz.p.edu.usersoap.dto.output;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AdminOutputDTO extends UserOutputDTO {
    private String favouriteIceCream;

    public AdminOutputDTO(UUID userId, String login, String favouriteIceCream, boolean active) {
        super(userId, login, UserType.ADMIN, active);
        this.setActive(active);
        this.favouriteIceCream = favouriteIceCream;
    }

    public AdminOutputDTO() {
        super(UserType.ADMIN);
    }
}
