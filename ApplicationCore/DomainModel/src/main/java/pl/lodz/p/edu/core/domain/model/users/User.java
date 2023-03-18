package pl.lodz.p.edu.core.domain.model.users;

import lombok.*;
import pl.lodz.p.edu.core.domain.model.AbstractModelData;

import java.util.UUID;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class User extends AbstractModelData {

    @Getter
    private final String login;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private boolean active;
    @Getter
    protected UserType userType;


    public User(String login, String password) {
        super(null);
        this.login = login;
        this.active = true;
        this.password = password;
    }

    public User(UUID id, String login, String password) {
        super(id);
        this.login = login;
        this.active = true;
        this.password = password;
    }

    public void update(User user) {
        this.password = user.password;
        this.active = user.active;
    }
}
