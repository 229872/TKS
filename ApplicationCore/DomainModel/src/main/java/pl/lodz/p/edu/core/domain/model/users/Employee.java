package pl.lodz.p.edu.core.domain.model.users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    private String desk;

    public Employee(String login, String password, String desk) {
        super(login, password);
        this.desk = desk;
        this.userType = UserType.EMPLOYEE;
    }

    public Employee(UUID id, String login, String password, String desk) {
        super(id, login, password);
        this.desk = desk;
        this.userType = UserType.EMPLOYEE;
    }

    @Override
    public void update(User user) {
        super.update(user);
        this.desk = ((Employee) user).desk;
    }
}
