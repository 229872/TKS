package entity.users;

import entity.AddressEnt;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("client")
public class ClientEnt extends UserEnt {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Embedded
    private AddressEnt address;


    public ClientEnt(String login, String password, String firstName, String lastName, AddressEnt address) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.userType = CLIENT_TYPE;
    }

    public ClientEnt() {
        address = new AddressEnt();
        this.userType = CLIENT_TYPE;
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

    public AddressEnt getAddress() {
        return address;
    }

    public void setAddress(AddressEnt address) {
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
}
