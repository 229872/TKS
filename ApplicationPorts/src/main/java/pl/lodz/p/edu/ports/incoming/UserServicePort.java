package pl.lodz.p.edu.ports.incoming;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.users.*;


import java.util.List;
import java.util.UUID;

public interface UserServicePort {
    List<User> searchOfType(String type, String login);

    List<User> getAllUsersOfType(String type);

    User getUserByUuidOfType(String type, UUID entityId);

    User getUserByLoginOfType(String type, String login);

    void activateUser(String type, UUID entityId);

    void deactivateUser(String type, UUID entityId);

    void registerEmployee(Employee employee) throws ConflictException;

    void updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException;

    void registerClient(Client client) throws ConflictException;

    void updateClient(UUID entityId, Client client) throws IllegalModificationException;

    void registerAdmin(Admin admin) throws ConflictException;

    void updateAdmin(UUID entityId, Admin admin) throws IllegalModificationException;
}
