package pl.lodz.p.edu.data.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.data.model.AbstractEntity;
import pl.lodz.p.edu.data.model.JsonPasswordCustomAdapter;
//import pl.lodz.p.edu.data.model.JsonCustomAdapter;

@Entity
@Table(name = "tuser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
//@JsonbTypeAdapter(c)
public abstract class User extends AbstractEntity {
    @Id
    @Column(name = "login")
    @NotEmpty
    private String login;

    @Column(name = "hash")
    @JsonbTypeAdapter(JsonPasswordCustomAdapter.class)
    private String password;

    @Column(name = "archive")
    private boolean active;

    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    protected UserType role;

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

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
