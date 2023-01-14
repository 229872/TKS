package pl.lodz.p.edu.data.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;

@Entity
@DiscriminatorValue("employee")
public class Employee extends User {

    @Column(name = "desk")
    @NotEmpty
    private String desk;

    public Employee() {}

    public Employee(String login, String password, String desk) {
        super(login, password);
        this.desk = desk;
        this.role = UserType.EMPLOYEE;
    }

    public Employee(EmployeeDTO employeeDTO) {
        this.merge(employeeDTO);
        this.role = UserType.EMPLOYEE;
    }



    public boolean verify() {
        return super.verify() && !desk.isEmpty();
    }

    public void merge(EmployeeDTO employeeDTO) {
        this.setLogin(employeeDTO.getLogin());
        this.setPassword(employeeDTO.getPassword());
        this.desk = employeeDTO.getDesk();
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public UserType getRole() {
        return role;
    }
}
