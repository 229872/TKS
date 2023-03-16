package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.users.*;
import pl.lodz.p.edu.ports.incoming.UserServicePort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class UserServicePortImpl implements UserServicePort {

    private final UserRepositoryPort userRepository;

    @Inject
    public UserServicePortImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) throws ConflictException {
        try {
            userRepository.add(user);
            return user;
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    @Override
    public List<User> getAllUsersOfType(UserType type) {
        return userRepository.getAll().stream()
                .filter(user -> user.getUserType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User get(UUID uuid) throws ObjectNotFoundServiceException {
        return userRepository.get(uuid);
    }

    @Override
    public User get(String login) throws ObjectNotFoundServiceException {
        return userRepository.getByLogin(login);
    }

    @Override
    public Client updateClient(UUID entityId, Client client) throws IllegalModificationException,
            ObjectNotFoundServiceException {
        try {
            Client clientDB = (Client) userRepository.get(entityId);
            clientDB.merge(client);
            return (Client) userRepository.update(clientDB);
        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public Admin updateAdmin(UUID entityId, Admin admin) throws IllegalModificationException, ObjectNotFoundServiceException {
        try {
            Admin adminDB = (Admin) userRepository.get(entityId);
            adminDB.merge(admin);
            return (Admin) userRepository.update(adminDB);

        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public Employee updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException,
            ObjectNotFoundServiceException {
        try {
            Employee employeeDB = (Employee) userRepository.get(entityId);
            employeeDB.merge(employee);
            return (Employee) userRepository.update(employeeDB);
        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    @Override
    public void activateUser(UUID entityId) throws ObjectNotFoundServiceException {
        synchronized (userRepository) {
            User user = userRepository.get(entityId);
            user.setActive(true);
            userRepository.update(user);
        }
    }

    @Override
    public void deactivateUser(UUID entityId) throws ObjectNotFoundServiceException {
        synchronized (userRepository) {
            User user = userRepository.get(entityId);
            user.setActive(false);
            userRepository.update(user);
        }
    }


    public void updateUser(User user)  {
        userRepository.update(user);
    }
}
