package pl.lodz.p.edu.mvc.controller;

import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.data.model.users.Employee;

@RequestScoped
public class EmployeeController extends UserController<Employee, EmployeeDTO> {
    private static final String path = "employees/";


    public EmployeeController() {
        super(path, Employee.class, Employee[].class, EmployeeDTO.class);
    }

}
