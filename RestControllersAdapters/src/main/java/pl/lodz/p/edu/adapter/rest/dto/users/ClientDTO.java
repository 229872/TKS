package pl.lodz.p.edu.adapter.rest.dto.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;

public class ClientDTO extends UserDTO {

    @NotEmpty(message = "First name can't be empty")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty")
    private String lastName;

    @NotNull(message = "Address can't be null")
    @Valid
    private AddressDTO address;

    public ClientDTO() {
        super(UserTypeDTO.CLIENT);
        address = new AddressDTO("", "", "");
    }


    public ClientDTO(String login, String password, String firstName, String lastName, AddressDTO address) {
        super(login, password, UserTypeDTO.CLIENT);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                '}';
    }
}
