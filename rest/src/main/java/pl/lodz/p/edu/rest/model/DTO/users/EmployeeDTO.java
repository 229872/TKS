package pl.lodz.p.edu.rest.model.DTO.users;

import pl.lodz.p.edu.rest.model.users.Employee;

public class EmployeeDTO extends UserDTO {
    private String desk;
    public EmployeeDTO() {}

    public EmployeeDTO(Employee e) {
        super(e);
        this.desk = e.getDesk();
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "desk='" + desk + '\'' +
                "} " + super.toString();
    }
}
