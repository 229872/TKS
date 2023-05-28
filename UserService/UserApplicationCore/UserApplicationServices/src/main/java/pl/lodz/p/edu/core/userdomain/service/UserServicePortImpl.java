package pl.lodz.p.edu.core.userdomain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.TransactionalException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ConflictException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.IllegalModificationException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.other.ClientEvent;
import pl.lodz.p.edu.user.core.domain.usermodel.users.*;
import pl.lodz.p.edu.userports.incoming.UserServicePort;
import pl.lodz.p.edu.userports.outgoing.RabbitPort;
import pl.lodz.p.edu.userports.outgoing.UserRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class UserServicePortImpl implements UserServicePort {

    private final UserRepositoryPort userRepository;

    private final RabbitPort<ClientEvent> rabbitPort;

    @Inject
    public UserServicePortImpl(UserRepositoryPort userRepository, RabbitPort<ClientEvent> rabbitPort) {
        this.userRepository = userRepository;
        this.rabbitPort = rabbitPort;
    }

    @Override
    public User registerUser(User user) throws ConflictException {
        try {
            User newUser = userRepository.add(user);

            if (newUser instanceof Client client) {
                rabbitPort.produce(new ClientEvent(client.getFirstName(), client.getLastName()));
            }
            return newUser;

        } catch(PersistenceException | TransactionalException e) {
            throw new ConflictException("Already exists user with given login"); //FIXME throw no login if rly no login
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
            clientDB.update(client);
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
            adminDB.update(admin);
            return (Admin) userRepository.update(adminDB);

        } catch (ClassCastException e) {
            throw new ObjectNotFoundServiceException("User not found");
        } catch(PersistenceException e) {
            throw new IllegalModificationException(e.getMessage());
        }
    }

    @Override
    public Employee updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException,
            ObjectNotFoundServiceException {
        try {
            Employee employeeDB = (Employee) userRepository.get(entityId);
            employeeDB.update(employee);
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

    @Override
    public void deleteUser(UUID entityId) throws ObjectNotFoundServiceException {
        User user = userRepository.get(entityId);
        userRepository.remove(user);
    }
}
