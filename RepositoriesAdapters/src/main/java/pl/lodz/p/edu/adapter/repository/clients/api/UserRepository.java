package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;

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
