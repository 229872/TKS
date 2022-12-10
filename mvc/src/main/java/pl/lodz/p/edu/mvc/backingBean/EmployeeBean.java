package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.data.model.users.Employee;
import pl.lodz.p.edu.mvc.controller.EmployeeController;
import pl.lodz.p.edu.mvc.controller.RentController;

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

//    private List<MvcRentDTO> clientRents;
//
//    public List<MvcRentDTO> getClientRents() {
//        return clientRents;
//    }

    public EmployeeBean() {}

    @PostConstruct
    public void init() {
        String clientId = getUuidFromParam();
        if (clientId == null) {
            employee = new Employee();
//            clientRents = new ArrayList<>();
        } else {
            employee = employeeController.get(clientId);
//            clientRents = rentController.getClientRents(clientId);
        }
    }

    public void update() {
        EmployeeDTO updatedEmployee = employeeController.update(
                employee.getEntityId().toString(), new EmployeeDTO(employee));
        employee.merge(updatedEmployee);
    }

    public void create() { //????????????????????????????????????????????????????????
        employee = employeeController.create(new EmployeeDTO(employee));
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
