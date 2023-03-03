package pl.lodz.p.edu.core.domain.model.users;

import pl.lodz.p.edu.core.domain.model.Address;


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

    public Client(Client elem) {
        super(elem.getLogin(), elem.getPassword());
        this.firstName = elem.getFirstName();
        this.lastName = elem.getLastName();
        this.address = elem.getAddress();
        this.userType = CLIENT_TYPE;
    }

    public void merge(Client client) {
        this.setLogin(client.getLogin());
        this.setPassword(client.getPassword());
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.address = client.getAddress();
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
