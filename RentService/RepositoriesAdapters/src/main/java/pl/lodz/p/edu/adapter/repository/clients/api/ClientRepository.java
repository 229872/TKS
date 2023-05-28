package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

public interface ClientRepository extends Repository<ClientEnt> {

    @Override
    ClientEnt update(ClientEnt elem);

    ClientEnt getByLogin(String login) throws EntityNotFoundRepositoryException;
}
