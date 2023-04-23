package pl.lodz.p.edu.adapter.repository.users.api;


import pl.lodz.p.edu.adapter.repository.users.data.UserEnt;
import pl.lodz.p.edu.adapter.repository.users.exception.EntityNotFoundRepositoryException;

public interface UserRepository extends Repository<UserEnt> {

    @Override
    UserEnt update(UserEnt elem);
    UserEnt getByLogin(String login) throws EntityNotFoundRepositoryException;
}
