package pl.lodz.p.edu.adapter.repository.users.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

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

    public UserEnt(UUID uuid, String login, String password, boolean active) {
        this.setEntityId(uuid);
        this.login = login;
        this.password = password;
        this.setActive(active);
    }

    public UserEnt(UUID uuid, Long version, String login, String password, boolean active) {
        this.setEntityId(uuid);
        this.setVersion(Objects.isNull(version) ? 0 : version);
        this.login = login;
        this.password = password;
        this.setActive(active);
    }

}
