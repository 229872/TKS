package pl.lodz.p.edu.userports.outgoing;

import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.User;

public interface UserRepositoryPort extends RepositoryPort<User> {
    @Override
    User update(User object);
    User getByLogin(String userLogin) throws ObjectNotFoundServiceException;
}
