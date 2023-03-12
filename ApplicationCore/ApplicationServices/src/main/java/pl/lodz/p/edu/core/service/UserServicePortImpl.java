package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.users.*;
import pl.lodz.p.edu.ports.incoming.UserServicePort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.util.List;
import java.util.UUID;

@Transactional
@RequestScoped
public class UserServicePortImpl implements UserServicePort {

    @Inject
    private UserRepositoryPort userRepository;

    protected UserServicePortImpl() {}

    @Override
    public void registerClient(Client client) throws ConflictException {
        try {
            userRepository.add(client);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    @Override
    public void registerAdmin(Admin admin) throws ConflictException {
        try {
            userRepository.add(admin);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    @Override
    public void registerEmployee(Employee employee) throws ConflictException {
        try {
            userRepository.add(employee);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    @Override
    public User getUserByUuidOfType(String type, UUID entityId) {
        return userRepository.getOfType(type, entityId);
    }

    @Override
    public List<User> getAllUsersOfType(String type) {
        return userRepository.getAllOfType(type);
    }

    @Override
    public List<User> searchOfType(String type, String login) {
        return userRepository.getAllWithLogin(type, login);
    }

    @Override
    public User getUserByLoginOfType(String type, String login) {
        return userRepository.getByLogin(type, login);
    }


    @Override
    public void updateClient(UUID entityId, Client client) throws IllegalModificationException {
        Client clientDB = (Client) userRepository.getOfType("Client", entityId);
        clientDB.merge(client);

        try {
            userRepository.update(clientDB);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public void updateAdmin(UUID entityId, Admin admin) throws IllegalModificationException {
        Admin adminDB = (Admin) userRepository.getOfType("Admin", entityId);
        adminDB.merge(admin);

        try {
            userRepository.update(adminDB);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public void updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException {
        Employee employeeDB = (Employee) userRepository.getOfType("Employee", entityId);
        employeeDB.merge(employee);

        try {
            userRepository.update(employeeDB);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public void activateUser(String type, UUID entityId) {
        User user;
        synchronized (userRepository) {
            user = userRepository.getOfType(type, entityId);
            user.setActive(true);
            userRepository.update(user);
        }
    }

    @Override
    public void deactivateUser(String type, UUID entityId) {
        User user;
        synchronized (userRepository) {
            user = userRepository.getOfType(type, entityId);
            user.setActive(false);
            userRepository.update(user);
        }
    }


    public void updateUser(User user)  {
        userRepository.update(user);
    }
}
