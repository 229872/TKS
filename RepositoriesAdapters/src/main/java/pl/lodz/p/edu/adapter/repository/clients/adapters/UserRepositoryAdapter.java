package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.users.*;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.users.*;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    private UserRepository repository;

    @Inject
    private UserFromDataToDomainMapper toDomainMapper;

    @Inject
    private UserFromDomainToDataMapper toDataMapper;

    @Override
    public User get(UUID entityId) throws ObjectNotFoundServiceException {
        try {
            UserEnt userEnt = repository.get(entityId);
            return convertToDomainModelFactory(userEnt);
        } catch (EntityNotFoundRepositoryException e) {
            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getAll() {
        return repository.getAll().stream()
                .map(this::convertToDomainModelFactory)
                .toList();
    }

    @Override
    public User add(User user) {
        return convertToDomainModelFactory(
                repository.add(convertToDataModelFactory(user)));
    }

    @Override
    public void remove(User user) {
        repository.remove(convertToDataModelFactory(user));
    }

    @Override
    public User getByLogin(String login) throws ObjectNotFoundServiceException {
        try {
            UserEnt userEnt = repository.getByLogin(login);
            return convertToDomainModelFactory(userEnt);
        } catch (EntityNotFoundRepositoryException e) {
            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public User update(User user) throws PersistenceException {
        UserEnt userEnt = repository.update(convertToPUTDataModelFactory(user));
        return convertToDomainModelFactory(userEnt);
    }

    private User convertToDomainModelFactory(UserEnt userEnt) {
        return switch (userEnt.getUserType()) {
            case ADMIN -> toDomainMapper.convertAdminToDomainModel((AdminEnt) userEnt);
            case EMPLOYEE -> toDomainMapper.convertEmployeeToDomainModel((EmployeeEnt) userEnt);
            case CLIENT -> toDomainMapper.convertClientToDomainModel((ClientEnt) userEnt);
        };
    }

    private UserEnt convertToDataModelFactory(User user) {
        return switch (user.getUserType()) {
            case ADMIN -> toDataMapper.convertAdminToDataModel((Admin) user);
            case EMPLOYEE -> toDataMapper.convertEmployeeToDataModel((Employee) user);
            case CLIENT -> toDataMapper.convertClientToDataModel((Client) user);
        };
    }

    private UserEnt convertToPUTDataModelFactory(User user) {
        return switch (user.getUserType()) {
            case ADMIN -> toDataMapper.convertPUTAdminToDataModel((Admin) user);
            case EMPLOYEE -> toDataMapper.convertPUTEmployeeToDataModel((Employee) user);
            case CLIENT -> toDataMapper.convertPUTClientToDataModel((Client) user);
        };
    }
}
