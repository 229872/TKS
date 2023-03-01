package entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

@Entity
@DiscriminatorValue("employee")
public class EmployeeEnt extends UserEnt {

    @Column(name = "desk")
    @NotEmpty
    private String desk;

    public EmployeeEnt() {}

    public EmployeeEnt(String login, String password, String desk) {
        super(login, password);
        this.desk = desk;
        this.userType = UserEnt.EMPLOYEE_TYPE;
    }

    public boolean verify() {
        return super.verify() && !desk.isEmpty();
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
