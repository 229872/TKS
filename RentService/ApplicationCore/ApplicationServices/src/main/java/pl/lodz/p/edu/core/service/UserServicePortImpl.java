package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.TransactionalException;
import pl.lodz.p.edu.core.domain.exception.IllegalDateException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.ports.incoming.UserServicePort;
import pl.lodz.p.edu.ports.outgoing.UserRepositoryPort;

import java.util.List;
import java.util.UUID;

@Dependent
public class UserServicePortImpl implements UserServicePort {

    private final UserRepositoryPort clientRepository;

    @Inject
    public UserServicePortImpl(UserRepositoryPort clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client registerUser(Client user) throws IllegalDateException {
        try {
            return clientRepository.add(user);
        } catch(PersistenceException | TransactionalException e) {
            throw new IllegalDateException(e.getMessage());
        }
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    @Override
    public Client get(UUID uuid) throws ObjectNotFoundServiceException {
        return clientRepository.get(uuid);
    }

    @Override
    public Client updateClient(UUID entityId, Client client) throws IllegalModificationException,
            ObjectNotFoundServiceException {
        try {
            Client clientDB = clientRepository.get(entityId);
            clientDB.update(client);
            return clientRepository.update(clientDB);
        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public Client updateClient(String login, String name, String lastName)
            throws IllegalModificationException, ObjectNotFoundServiceException {
        try {
            Client clientDB = clientRepository.getByLogin(login);
            clientDB.update(name, lastName);
            return clientRepository.update(clientDB);
        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }
}
