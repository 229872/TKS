package pl.lodz.p.edu.adapter.rest.dto.users;

import jakarta.validation.constraints.NotEmpty;

public class EmployeeDTO extends UserDTO {

    @NotEmpty
    private String desk;

    public EmployeeDTO() {
        super(UserTypeDTO.EMPLOYEE);
    }

    public EmployeeDTO(String login, String password, String desk) {
        super(login, password, UserTypeDTO.EMPLOYEE);
        this.desk = desk;
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
