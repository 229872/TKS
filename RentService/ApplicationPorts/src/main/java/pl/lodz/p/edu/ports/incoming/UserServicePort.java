package pl.lodz.p.edu.ports.incoming;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.users.*;


import java.util.List;
import java.util.UUID;

public interface UserServicePort {

    List<User> getAllUsersOfType(UserType type);
    List<User> getAll();
    User get(UUID uuid) throws ObjectNotFoundServiceException;
    User get(String login) throws ObjectNotFoundServiceException;
    void activateUser(UUID entityId) throws ObjectNotFoundServiceException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundServiceException;

    User registerUser(User user) throws ConflictException;

    Employee updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException, ObjectNotFoundServiceException;

    Client updateClient(UUID entityId, Client client) throws IllegalModificationException, ObjectNotFoundServiceException;

    Admin updateAdmin(UUID entityId, Admin admin) throws IllegalModificationException, ObjectNotFoundServiceException;
}
