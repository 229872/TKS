package pl.lodz.p.edu.adapter.repository.clients.data.users;

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


    public AdminEnt() {

    }

    public AdminEnt(String login, String password) {
        super(login, password);
    }

    public AdminEnt(UUID uuid, String login, String password, boolean active) {
        super(uuid, login, password, active);
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.ADMIN;
    }
}
