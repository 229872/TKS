package clients.adapters;

import clients.adapters.mapper.UserFromDataToDomainMapper;
import clients.adapters.mapper.UserFromDomainToDataMapper;
import clients.api.UserRepository;
import clients.data.users.AdminEnt;
import clients.data.users.ClientEnt;
import clients.data.users.EmployeeEnt;
import clients.data.users.UserEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.model.users.Admin;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.model.users.Employee;
import pl.lodz.p.edu.model.users.User;
import pl.lodz.p.edu.outcoming.UserRepositoryPort;

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
    public Long count() {
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
        return  convertToDomainModelFactory(userEnt);
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
