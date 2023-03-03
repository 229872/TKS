package clients.api;

import clients.data.users.UserEnt;
import pl.lodz.p.edu.model.users.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends Repository<UserEnt> {
    UserEnt getOfType(String type, UUID entityId);

    List<UserEnt> getAllOfType(String type);

    List<UserEnt> getAllWithLogin(String type, String login);

    UserEnt getByLogin(String type, String login);

    UserEnt getByLoginPassword(String login, String password);
    @Override
    void update(UserEnt elem);
}
