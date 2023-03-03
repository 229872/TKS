package pl.lodz.p.edu.model.users;

import pl.lodz.p.edu.DTO.users.ClientDTO;
import pl.lodz.p.edu.model.Address;


public class Client extends User {


    private String firstName;


    private String lastName;

    private Address address;


    public Client(String login, String password, String firstName, String lastName, Address address) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.userType = CLIENT_TYPE;
    }

    public Client(ClientDTO clientDTO) {
        super(clientDTO.getLogin(), clientDTO.getPassword());
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
        this.address = clientDTO.getAddress();
        this.userType = CLIENT_TYPE;
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
        this.userType = CLIENT_TYPE;
    }

    public Client() {
        address = new Address();
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
