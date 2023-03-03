package clients.data.users;

import clients.data.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tuser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class UserEnt extends AbstractEntity {

    public static String CLIENT_TYPE = "CLIENT";
    public static String EMPLOYEE_TYPE = "EMPLOYEE";
    public static String ADMIN_TYPE = "ADMIN";


    @Id
    @Column(name = "login")
    @NotEmpty
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "archive")
    private boolean active;

    @Column(name = "userType")
    protected String userType;

    public UserEnt(String login, String password) {
        this.login = login;
        this.active = true;
        this.password = password;
    }

    public UserEnt() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", active=" + active +
                ", abstractEntity=" + super.toString() +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void merge(UserEnt user) {
        this.login = user.login;
        this.active = user.active;
    }
}
