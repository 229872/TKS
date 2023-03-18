package pl.lodz.p.edu.adapter.repository.clients.data.users;

import lombok.*;
import pl.lodz.p.edu.adapter.repository.clients.data.AbstractEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tuser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = UserEnt.FIND_ALL, query = "SELECT usr FROM UserEnt usr")
@NamedQuery(name = UserEnt.FIND_BY_ID, query = "SELECT usr FROM UserEnt usr WHERE usr.id = :id")
@NamedQuery(name = UserEnt.FIND_BY_LOGIN, query = "SELECT usr FROM UserEnt usr WHERE usr.login = :login")
public abstract class UserEnt extends AbstractEntity {
    public static final String FIND_ALL = "UserEnt.findAll";
    public static final String FIND_BY_ID = "UserEnt.findById";
    public static final String FIND_BY_LOGIN = "UserEnt.findByLogin";

    @Column(unique = true)
    private String login;
    private String password;
    private boolean active;

    abstract public UserTypeEnt getUserType();

    public UserEnt(String login, String password) {
        this.login = login;
        this.active = true;
        this.password = password;
    }
}
