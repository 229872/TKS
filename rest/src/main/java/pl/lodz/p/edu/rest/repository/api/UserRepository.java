package pl.lodz.p.edu.rest.repository.api;

import pl.lodz.p.edu.data.model.users.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends Repository<User> {
    User getOfType(String type, UUID entityId);

    List<User> getAllOfType(String type);

    List<User> getAllWithLogin(String type, String login);

    User getByLogin(String type, String login);

    User getByLoginPassword(String login, String password);
}
