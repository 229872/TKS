package pl.lodz.p.edu.adapter.rest.users.dto.output.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.adapter.rest.users.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.UserTypeDTO;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ClientOutputDTO extends UserOutputDTO {
    private String firstName;
    private String lastName;
    private AddressDTO address;

    public ClientOutputDTO() {
        super(UserTypeDTO.CLIENT);
        address = new AddressDTO("", "", "");
    }

    public ClientOutputDTO(UUID userId, String login, String firstName, String lastName, boolean active, AddressDTO address) {
        super(userId, login, UserTypeDTO.CLIENT, active);
        this.setActive(active);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
