package pl.lodz.p.edu.adapter.repository.clients.data.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import java.util.UUID;


@Entity
@DiscriminatorValue("client")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClientEnt extends UserEnt {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public ClientEnt(String login, String password, String firstName, String lastName) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ClientEnt(UUID uuid, String login, String password, String firstName, String lastName, boolean active) {
        super(uuid, login, password, active);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected ClientEnt() {
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.CLIENT;
    }
}
