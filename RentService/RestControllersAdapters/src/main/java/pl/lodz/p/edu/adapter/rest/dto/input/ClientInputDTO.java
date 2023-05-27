package pl.lodz.p.edu.adapter.rest.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;

@Getter
@Setter
@ToString
public class ClientInputDTO {

    @NotBlank(message = "{user.firstName.empty}")
    private String firstName;

    @NotBlank(message = "{user.lastName.empty}")
    private String lastName;

    public ClientInputDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
