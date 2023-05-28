package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;


@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = ClientEnt.FIND_ALL, query = "SELECT usr FROM ClientEnt usr")
@NamedQuery(name = ClientEnt.FIND_BY_ID, query = "SELECT usr FROM ClientEnt usr WHERE usr.id = :id")
@NamedQuery(name = ClientEnt.FIND_BY_LOGIN, query = "SELECT usr FROM ClientEnt usr WHERE usr.login = :login")
public class ClientEnt extends AbstractEntity {

    public static final String FIND_ALL = "ClientEnt.findAll";
    public static final String FIND_BY_ID = "ClientEnt.findById";
    public static final String FIND_BY_LOGIN = "ClientEnt.findByLogin";

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login")
    private String login;

    public ClientEnt(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ClientEnt(UUID uuid, String login, String firstName, String lastName) {
        super(uuid);
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected ClientEnt() {
    }

}
