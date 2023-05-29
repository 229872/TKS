package pl.lodz.p.edu.adapter.repository.users.data;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    @Embedded
    private AddressEnt address;


    public ClientEnt(String login, String password, String firstName, String lastName, AddressEnt address) {
        super(login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public ClientEnt(UUID uuid, String login, String password, String firstName, String lastName, boolean active, AddressEnt address) {
        super(uuid, login, password, active);
        this.firstName = firstName;
        this.lastName = lastName;;
        this.address = address;
    }

    public ClientEnt(UUID uuid, Long version, String login, String password, String firstName, String lastName, boolean active, AddressEnt address) {
        super(uuid, version, login, password, active);
        this.firstName = firstName;
        this.lastName = lastName;;
        this.address = address;
    }

    protected ClientEnt() {
        address = new AddressEnt();
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.CLIENT;
    }
}
