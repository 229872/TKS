package pl.lodz.p.edu.rest.managers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.users.Employee;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.rest.repository.impl.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Transactional
@RequestScoped
public class UserManager {

    Logger logger = Logger.getLogger(UserManager.class.getName());

    @Inject
    private UserRepository userRepository;

    protected UserManager() {}

    // ========================================= create

    public void registerClient(Client client) throws ConflictException {
        try {
            userRepository.add(client);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    public void registerAdmin(Admin admin) throws ConflictException {
        try {
            userRepository.add(admin);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    public void registerEmployee(Employee employee) throws ConflictException {
        try {
            userRepository.add(employee);
        } catch(PersistenceException e) {
            throw new ConflictException("Already exists user with given login");
        }
    }

    // ========================================= read

    public User getUserByUuidOfType(String type, UUID entityId) {
        return userRepository.getOfType(type, entityId);
    }

    public List<User> getAllUsersOfType(String type) {
        return userRepository.getAllOfType(type);
    }

    public List<User> searchOfType(String type, String login) {
        return userRepository.getAllWithLogin(type, login);
    }

    public User getUserByLoginOfType(String type, String login) {
        return userRepository.getByLogin(type, login);
    }

    // ========================================= update

    public void updateClient(UUID entityId, ClientDTO clientDTO) throws IllegalModificationException {
        Client client = (Client) userRepository.getOfType("Client", entityId);
        client.merge(clientDTO);

        try {
            userRepository.update(client);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    public void updateAdmin(UUID entityId, AdminDTO adminDTO) throws IllegalModificationException {
        Admin admin = (Admin) userRepository.getOfType("Admin", entityId);
        admin.merge(adminDTO);

        try {
            userRepository.update(admin);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }
    public void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws IllegalModificationException {
        Employee employee = (Employee) userRepository.getOfType("Employee", entityId);
        employee.merge(employeeDTO);

        try {
            userRepository.update(employee);
        } catch(PersistenceException e) {
            throw new IllegalModificationException("Cannot modify clients login");
        }
    }

    public void activateUser(String type, UUID entityId) {
        User user;
        synchronized (userRepository) {
            user = userRepository.getOfType(type, entityId);
            user.setActive(true);
            userRepository.update(user);
        }
    }

    public void deactivateUser(String type, UUID entityId) {
        User user;
        synchronized (userRepository) {
            user = userRepository.getOfType(type, entityId);
            user.setActive(false);
            userRepository.update(user);
        }
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }
}
