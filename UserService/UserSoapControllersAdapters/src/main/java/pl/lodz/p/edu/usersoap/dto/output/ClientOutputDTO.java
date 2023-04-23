package pl.lodz.p.edu.usersoap.dto.output;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;
import pl.lodz.p.edu.usersoap.dto.AddressDTO;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ClientOutputDTO extends UserOutputDTO {
    private String firstName;
    private String lastName;
    private AddressDTO address;

    public ClientOutputDTO() {
        super(UserType.CLIENT);
        address = new AddressDTO("", "", "");
    }

    public ClientOutputDTO(UUID userId, String login, String firstName, String lastName, boolean active, AddressDTO address) {
        super(userId, login, UserType.CLIENT, active);
        this.setActive(active);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
