package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.data.model.users.Employee;
import pl.lodz.p.edu.mvc.controller.EmployeeController;
import pl.lodz.p.edu.mvc.controller.RentController;

import java.io.IOException;
import java.net.http.HttpResponse;

@Named
@RequestScoped
public class EmployeeBean extends AbstractBean {
    @Inject
    private EmployeeController employeeController;

    @Inject
    private RentController rentController;

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private String ifMatch = "";

    public String getIfMatch() {
        return ifMatch;
    }

    public void setIfMatch(String ifMatch) {
        this.ifMatch = ifMatch;
    }


    public EmployeeBean() {}

    @PostConstruct
    public void init() {
        String clientId = getUuidFromParam();
        if (clientId == null) {
            employee = new Employee();
//            clientRents = new ArrayList<>();
        } else {
            try {
                HttpResponse<String> response = employeeController.getWithRequest(clientId);
                employee = om.readValue(response.body(), Employee.class);

                if (response.headers().firstValue("ETag").isEmpty()) {
                    throw new RuntimeException("no eTag header");
                }
                String eTag = response.headers().firstValue("ETag").get();

                ifMatch = om.readValue(eTag, String.class);

            } catch (IOException e) {
                throw new RuntimeException(e); // todo komunikat
            }
        }
    }

    public void update() {
        EmployeeDTO updatedEmployee = employeeController.update(
                employee.getEntityId().toString(), new EmployeeDTO(employee), ifMatch);
        employee.merge(updatedEmployee);
    }

    public void create() {
        employee = employeeController.create(new EmployeeDTO(employee));
        employee.setPassword("");
    }


    public void activate() {
        employeeController.activate(employee.getEntityId().toString());
        employee = employeeController.get(employee.getEntityId().toString());
    }

    public void deactivate() {
        employeeController.deactivate(employee.getEntityId().toString());
        employee = employeeController.get(employee.getEntityId().toString());
    }

}
