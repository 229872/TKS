package pl.lodz.p.edu.core.domain.model.users;

import lombok.*;
import pl.lodz.p.edu.core.domain.model.Address;
import java.util.UUID;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    private String firstName;
    private String lastName;
    private Address address;


    public Client(String login, String password, String firstName, String lastName, Address address) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.userType = UserType.CLIENT;
    }

    public Client(UUID id, String login, String password, String firstName, String lastName, boolean active, Address address) {
        super(id, login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.setActive(active);
        this.address = address;
        this.userType = UserType.CLIENT;
    }

    public Client(Client elem) {
        super(elem.getLogin(), elem.getPassword());
        this.firstName = elem.getFirstName();
        this.lastName = elem.getLastName();
        this.address = elem.getAddress();
        this.userType = UserType.CLIENT;
    }

    @Override
    public void update(User user) {
        super.update(user);
        Client client = (Client) user;
        this.firstName = client.firstName;
        this.lastName = client.lastName;
        this.address = client.address;
    }
}
