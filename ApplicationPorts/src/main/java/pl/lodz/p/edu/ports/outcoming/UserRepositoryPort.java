package pl.lodz.p.edu.ports.outcoming;

import pl.lodz.p.edu.core.domain.model.users.User;

public interface UserRepositoryPort extends RepositoryPort<User> {
    @Override
    User update(User object);
    User getByLogin(String userLogin);
}
