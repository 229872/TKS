package pl.lodz.p.edu.adapter.repository.clients.data.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.ADMIN;
    }
}
