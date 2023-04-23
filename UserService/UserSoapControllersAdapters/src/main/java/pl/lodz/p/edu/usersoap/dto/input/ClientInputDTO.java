package pl.lodz.p.edu.usersoap.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;
import pl.lodz.p.edu.usersoap.dto.AddressDTO;

@Getter
@Setter
@ToString
public class ClientInputDTO extends UserInputDTO {

    @NotBlank(message = "{user.firstName.empty}")
    private String firstName;

    @NotBlank(message = "{user.lastName.empty}")
    private String lastName;

    @NotNull(message = "{address.null}")
    @Valid
    private AddressDTO address;

    public ClientInputDTO() {
        super(UserType.CLIENT);
        address = new AddressDTO("", "", "");
    }


    public ClientInputDTO(String login, String password, String firstName, String lastName, AddressDTO address) {
        super(login, password, UserType.CLIENT);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
