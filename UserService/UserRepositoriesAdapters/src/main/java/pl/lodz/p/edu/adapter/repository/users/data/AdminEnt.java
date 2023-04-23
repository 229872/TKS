package pl.lodz.p.edu.adapter.repository.users.data;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@DiscriminatorValue("admin")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AdminEnt extends UserEnt {
    @Column(name = "favourite_ice_cream")
    private String favouriteIceCream;

    public AdminEnt() {

    }

    public AdminEnt(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
    }

    public AdminEnt(UUID uuid, String login, String password, boolean active, String favouriteIceCream) {
        super(uuid, login, password, active);
        this.favouriteIceCream = favouriteIceCream;
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.ADMIN;
    }
}
