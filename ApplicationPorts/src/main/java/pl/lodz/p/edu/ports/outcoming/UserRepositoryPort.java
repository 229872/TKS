package pl.lodz.p.edu.ports.outcoming;

import pl.lodz.p.edu.core.domain.model.users.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryPort extends RepositoryPort<User> {
    User getOfType(String type, UUID entityId);

    List<User> getAllOfType(String type);

    List<User> getAllWithLogin(String type, String login);

    User getByLogin(String type, String login);

    User getByLoginPassword(String login, String password);
    @Override
    void update(User elem);
}
