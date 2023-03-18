package pl.lodz.p.edu.adapter.repository.clients.data.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @Override
    public UserTypeEnt getUserType() {
        return UserTypeEnt.EMPLOYEE;
    }
}
