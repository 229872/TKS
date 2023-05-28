package pl.lodz.p.edu.userports.incoming;

import pl.lodz.p.edu.user.core.domain.usermodel.exception.ConflictException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.IllegalModificationException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.*;

import java.util.List;
import java.util.UUID;

public interface UserServicePort {
    List<User> getAllUsersOfType(UserType type);
    List<User> getAll();
    User get(UUID uuid) throws ObjectNotFoundServiceException;
    User get(String login) throws ObjectNotFoundServiceException;
    void activateUser(UUID entityId) throws ObjectNotFoundServiceException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundServiceException;

    void deleteUser(UUID entityId) throws ObjectNotFoundServiceException;

    void deleteUser(String login) throws ObjectNotFoundServiceException;

    User registerUser(User user) throws ConflictException;

    Employee updateEmployee(UUID entityId, Employee employee) throws IllegalModificationException, ObjectNotFoundServiceException;

    Client updateClient(UUID entityId, Client client) throws IllegalModificationException, ObjectNotFoundServiceException;

    Admin updateAdmin(UUID entityId, Admin admin) throws IllegalModificationException, ObjectNotFoundServiceException;

}
