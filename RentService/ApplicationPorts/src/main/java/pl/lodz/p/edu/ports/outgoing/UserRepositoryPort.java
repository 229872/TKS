package pl.lodz.p.edu.ports.outgoing;

import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryPort {

    Client get(UUID objectId) throws ObjectNotFoundServiceException;

    Client getByLogin(String login) throws ObjectNotFoundServiceException;

    List<Client> getAll();

    Client add(Client object);

    Client update(Client object);
}
