package pl.lodz.p.edu.adapter.repository.clients.data.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@DiscriminatorValue("employee")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeEnt extends UserEnt {

    public EmployeeEnt() {}

    public EmployeeEnt(String login, String password) {
        super(login, password);
    }

    public EmployeeEnt(UUID uuid, String login, String password, boolean active) {
        super(login, password);
        this.setEntityId(uuid);
        this.setActive(active);
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.EMPLOYEE;
    }
}
