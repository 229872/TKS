package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Employee;
import pl.lodz.p.edu.mvc.controller.EmployeeController;

import javax.faces.bean.SessionScoped;
import java.util.List;

@Named
@SessionScoped
public class EmployeeListBean {
    @Inject
    private EmployeeController employeeController;

    public EmployeeListBean() {}

    @PostConstruct
    public void initEmployees() {
        employees = employeeController.getAll();
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
