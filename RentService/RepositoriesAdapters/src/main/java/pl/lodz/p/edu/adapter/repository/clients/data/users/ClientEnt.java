package pl.lodz.p.edu.adapter.repository.clients.data.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.lodz.p.edu.adapter.repository.clients.data.AbstractEntity;
import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;

import java.util.UUID;


@Entity
@DiscriminatorValue("client")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = ClientEnt.FIND_ALL, query = "SELECT usr FROM ClientEnt usr")
@NamedQuery(name = ClientEnt.FIND_BY_ID, query = "SELECT usr FROM ClientEnt usr WHERE usr.id = :id")
public class ClientEnt extends AbstractEntity {

    public static final String FIND_ALL = "ClientEnt.findAll";
    public static final String FIND_BY_ID = "ClientEnt.findById";

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public ClientEnt(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ClientEnt(UUID uuid, String firstName, String lastName) {
        super(uuid);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected ClientEnt() {
    }

}
