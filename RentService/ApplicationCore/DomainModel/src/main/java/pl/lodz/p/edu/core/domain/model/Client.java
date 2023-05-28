package pl.lodz.p.edu.core.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Client extends AbstractModelData {

    private String login;
    private String name;
    private String lastName;

    public Client(UUID entityId, String login, String name, String lastName) {
        super(entityId);
        this.login = login;
        this.name = name;
        this.lastName = lastName;
    }

    public void update(Client client) {
        this.name = client.getName();
        this.lastName = client.getLastName();
    }

    public void update(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
}
