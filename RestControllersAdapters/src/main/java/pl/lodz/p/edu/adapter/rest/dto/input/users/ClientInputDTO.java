package pl.lodz.p.edu.adapter.rest.dto.input.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;
import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;

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
        super(UserTypeDTO.CLIENT);
        address = new AddressDTO("", "", "");
    }


    public ClientInputDTO(String login, String password, String firstName, String lastName, AddressDTO address) {
        super(login, password, UserTypeDTO.CLIENT);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
