package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.model.Address;

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

    public Client(String login, String firstName, String lastName, Address address) {
        super(login);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Client(ClientDTO clientDTO) {
        super(clientDTO.getLogin());
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.address = clientDTO.getAddress();
    }

    public boolean verify() {
        return super.verify() && !firstName.isEmpty()
                && !lastName.isEmpty() && address.verify();
    }

    public void merge(ClientDTO clientDTO) {
        this.setLogin(clientDTO.getLogin());
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.address = clientDTO.getAddress();
    }

    public Client() {}

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
}
