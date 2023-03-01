package pl.lodz.p.edu.rest.model.users;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import pl.lodz.p.edu.rest.model.AbstractModelData;
import pl.lodz.p.edu.rest.model.JsonPasswordCustomAdapter;
//import pl.lodz.p.edu.data.model.JsonCustomAdapter;


//@JsonbTypeAdapter(c)
public abstract class User extends AbstractModelData {

    public static String CLIENT_TYPE = "CLIENT";
    public static String EMPLOYEE_TYPE = "EMPLOYEE";
    public static String ADMIN_TYPE = "ADMIN";

    private String login;

    @JsonbTypeAdapter(JsonPasswordCustomAdapter.class)
    //Fixme instead adapter dto
    private String password;

    private boolean active;

    protected String userType;

    public User(String login, String password) {
        this.login = login;
        this.active = true;
        this.password = password;
    }

    public User() {}

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

    public void merge(User user) {
        this.login = user.login;
        this.active = user.active;
    }

    public boolean verify() {
        return !login.isEmpty();
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
}
