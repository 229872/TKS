package pl.lodz.p.edu.adapter.repository.users.data;

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

    @Column(name = "desk")
    private String desk;

    public EmployeeEnt() {}

    public EmployeeEnt(String login, String password, String desk) {
        super(login, password);
        this.desk = desk;
    }

    public EmployeeEnt(UUID uuid, String login, String password, boolean active, String desk) {
        super(login, password);
        this.setEntityId(uuid);
        this.setActive(active);
        this.desk = desk;
    }

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.EMPLOYEE;
    }
}
