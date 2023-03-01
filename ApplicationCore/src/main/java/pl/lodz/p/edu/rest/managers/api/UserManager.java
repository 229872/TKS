package pl.lodz.p.edu.rest.managers.api;

import pl.lodz.p.edu.rest.DTO.users.AdminDTO;
import pl.lodz.p.edu.rest.DTO.users.ClientDTO;
import pl.lodz.p.edu.rest.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.rest.model.users.Admin;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.Employee;
import pl.lodz.p.edu.rest.model.users.User;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface UserManager {
    List<User> searchOfType(String type, String login);

    List<User> getAllUsersOfType(String type);

    User getUserByUuidOfType(String type, UUID entityId);

    User getUserByLoginOfType(String type, String login);

    void activateUser(String type, UUID entityId);

    void deactivateUser(String type, UUID entityId);

    void registerEmployee(Employee employee) throws ConflictException;

    void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws IllegalModificationException;

    void registerClient(Client client) throws ConflictException;

    void updateClient(UUID entityId, ClientDTO clientDTO) throws IllegalModificationException;

    void registerAdmin(Admin admin) throws ConflictException;

    void updateAdmin(UUID entityId, AdminDTO adminDTO) throws IllegalModificationException;
}
