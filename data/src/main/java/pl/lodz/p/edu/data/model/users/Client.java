package pl.lodz.p.edu.data.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.data.model.Address;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;

@Entity
@DiscriminatorValue("client")
public class Client extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Embedded
    private Address address;


    public Client(String login, String password, String firstName, String lastName, Address address) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.role = UserType.CLIENT;
    }

    public Client(ClientDTO clientDTO) {
        super(clientDTO.getLogin(), clientDTO.getPassword());
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.address = clientDTO.getAddress();
        this.role = UserType.CLIENT;
    }

    public boolean verify() {
        return super.verify() && !firstName.isEmpty()
                && !lastName.isEmpty() && address.verify();
    }

    public void merge(ClientDTO clientDTO) {
        this.setLogin(clientDTO.getLogin());
        this.setPassword(clientDTO.getPassword());
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.address = clientDTO.getAddress();
    }

    public Client() {
        address = new Address();
        this.role = UserType.CLIENT;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                "} " + super.toString();
    }

    public void updateClientData(String firstName, String lastName, Address address) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
    }

    public UserType getRole() {
        return role;
    }

}
