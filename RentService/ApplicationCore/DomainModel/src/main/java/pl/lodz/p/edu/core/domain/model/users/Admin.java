package pl.lodz.p.edu.core.domain.model.users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    private String favouriteIceCream;


    public Admin(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
        this.userType = UserType.ADMIN;
    }

    public Admin(UUID id, String login, String password, boolean active, String favouriteIceCream) {
        super(id, login, password);
        this.setActive(active);
        this.favouriteIceCream = favouriteIceCream;
        this.userType = UserType.ADMIN;
    }

    @Override
    public void update(User user) {
        super.update(user);
        this.favouriteIceCream = ((Admin) user).favouriteIceCream;
    }
}
