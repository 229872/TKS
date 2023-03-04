package pl.lodz.p.edu.adapter.repository.clients.adapters;

import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.users.AdminEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.EmployeeEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;
import pl.lodz.p.edu.core.domain.model.users.User;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.util.List;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    private UserRepository repository;

    @Inject
    private UserFromDataToDomainMapper toDomainMapper;

    @Inject
    private UserFromDomainToDataMapper toDataMapper;

    @Override
    public User get(UUID entityId) {
        UserEnt userEnt = repository.get(entityId);
        return convertToDomainModelFactory(userEnt);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll().stream()
                .map(this::convertToDomainModelFactory)
                .toList();
    }

    @Override
    public void add(User elem) {
        repository.add(convertToDataModelFactory(elem));
    }

    @Override
    public void remove(UUID entityId) {
        repository.remove(entityId);
    }

    @Override
    public long count() {
        return repository.count();
    }

//fixme maybe refactor it later
    @Override
    public User getOfType(String type, UUID entityId) {
        UserEnt userEnt = repository.getOfType(type, entityId);
        return convertToDomainModelFactory(userEnt);
    }

    @Override
    public List<User> getAllOfType(String type) {
        return repository.getAllOfType(type).stream()
                .map(this::convertToDomainModelFactory)
                .toList();
    }

    @Override
    public List<User> getAllWithLogin(String type, String login) {
        return repository.getAllWithLogin(type, login).stream()
                .map(this::convertToDomainModelFactory)
                .toList();
    }

    @Override
    public User getByLogin(String type, String login) {
        UserEnt userEnt = repository.getByLogin(type, login);
        return convertToDomainModelFactory(userEnt);
    }

    //fixme change
    @Override
    public User getByOnlyLogin(String login) {
        UserEnt userEnt = repository.getByOnlyLogin(login);
        return convertToDomainModelFactory(userEnt);
    }

    @Override
    public User getByLoginPassword(String login, String password) {
        UserEnt userEnt = repository.getByLoginPassword(login, password);
        return convertToDomainModelFactory(userEnt);
    }

    @Override
    public void update(User elem) {
        UserEnt userEnt = convertToDataModelFactory(elem);
        repository.update(userEnt);
    }


    //fixme change string to enum
    private User convertToDomainModelFactory(UserEnt userEnt) {
      return switch (userEnt.getUserType()) {
            case "ADMIN" -> toDomainMapper.convertAdminToDomainModel((AdminEnt) userEnt);
            case "EMPLOYEE" -> toDomainMapper.convertEmployeeToDomainModel((EmployeeEnt) userEnt);
            case "CLIENT" -> toDomainMapper.convertClientToDomainModel((ClientEnt) userEnt);
        };
    }

    private UserEnt convertToDataModelFactory(User user) {
        return switch (user.getUserType()) {
            case "ADMIN" -> toDataMapper.convertAdminToDataModel((Admin) user);
            case "EMPLOYEE" -> toDataMapper.convertEmployeeToDataModel((Employee) user);
            case "CLIENT" -> toDataMapper.convertClientToDataModel((Client) user);
        };
    }
}
