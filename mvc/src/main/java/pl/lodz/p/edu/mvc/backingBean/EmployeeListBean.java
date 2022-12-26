package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Employee;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.EmployeeController;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class EmployeeListBean implements Serializable {
    @Inject
    private EmployeeController employeeController;

    public EmployeeListBean() {}

    @PostConstruct
    public void initEmployees() {
        employees = employeeController.getAll();
        employees.sort(Comparator.comparing(User::getLogin));
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }


    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }


    public void search() {
        employees = employeeController.search(searchParam);
    }
}
