package pl.lodz.p.edu.ports.incoming;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;


import java.util.List;
import java.util.UUID;

public interface UserServicePort {

    List<Client> getAll();

    Client get(UUID uuid) throws ObjectNotFoundServiceException;

    Client registerUser(Client user) throws ConflictException;

    Client updateClient(UUID entityId, Client client) throws IllegalModificationException, ObjectNotFoundServiceException;
}
