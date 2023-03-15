package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundException;


public interface UserRepository extends Repository<UserEnt> {

    @Override
    UserEnt update(UserEnt elem);
    UserEnt getByLogin(String login) throws EntityNotFoundException;
}
