package pl.lodz.p.edu.usersoap.dto.input;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

@Getter
@Setter
@ToString
public class AdminInputDTO extends UserInputDTO {

    @NotBlank(message = "{iceCream.empty}")
    private String favouriteIceCream;

    public AdminInputDTO() {
        super(UserType.ADMIN);
    }

    public AdminInputDTO(String login, String password, String favouriteIceCream) {
        super(login, password, UserType.ADMIN);
        this.favouriteIceCream = favouriteIceCream;
    }
}
