package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;

// fixme refactor to ClientRepository
public interface UserRepository extends Repository<ClientEnt> {

    @Override
    ClientEnt update(ClientEnt elem);
}
