package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.ClientRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.ports.outgoing.UserRepositoryPort;

import java.util.List;
import java.util.UUID;

//fixme refactor to ClientRepositoryAdapter
@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    private ClientRepository repository;

    @Inject
    private UserFromDataToDomainMapper toDomainMapper;

    @Inject
    private UserFromDomainToDataMapper toDataMapper;

    @Override
    public Client get(UUID entityId) throws ObjectNotFoundServiceException {
        try {
            ClientEnt client = repository.get(entityId);
            return toDomainMapper.convertClientToDomainModel(client);
        } catch (EntityNotFoundRepositoryException e) {
            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Client getByLogin(String login) throws ObjectNotFoundServiceException {
        try {
            ClientEnt client = repository.getByLogin(login);
            return toDomainMapper.convertClientToDomainModel(client);
        } catch (EntityNotFoundRepositoryException e) {
            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public List<Client> getAll() {
        return repository.getAll().stream()
                .map(this::convertClientToDomainModelALL)
                .toList();
    }

    @Override
    public Client add(Client user) {
        return convertClientToDomainModelALL(
                repository.add(toDataMapper.convertClientToDataModelCreate(user)));
    }


    @Override
    public Client update(Client user) throws PersistenceException {
        ClientEnt userEnt = repository.update(convertClientToPUTDataModel(user));
        return convertClientToDomainModelALL(userEnt);
    }


    private Client convertClientToDomainModelALL(ClientEnt clientEnt) {
        return toDomainMapper.convertClientToDomainModel(clientEnt);
    }

    private ClientEnt convertClientToPUTDataModel(Client client) {
        return toDataMapper.convertPUTClientToDataModel(client);
    }
}
