package pl.lodz.p.edu.core.domain.model.users;

public class Employee extends User {

    private String desk;

    public Employee() {}

    public Employee(String login, String password, String desk) {
        super(login, password);
        this.desk = desk;
        this.userType = User.EMPLOYEE_TYPE;
    }

    public void merge(Employee employee) {
        this.setLogin(employee.getLogin());
        this.setPassword(employee.getPassword());
        this.desk = employee.getDesk();
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
