package pl.lodz.p.edu.adapter.rest.dto.input.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;

@Getter
@Setter
@ToString
public class AdminInputDTO extends UserInputDTO {

    @NotBlank(message = "{iceCream.empty}")
    private String favouriteIceCream;

    public AdminInputDTO() {
        super(UserTypeDTO.ADMIN);
    }

    public AdminInputDTO(String login, String password, String favouriteIceCream) {
        super(login, password, UserTypeDTO.ADMIN);
        this.favouriteIceCream = favouriteIceCream;
    }
}
