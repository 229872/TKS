package pl.lodz.p.edu.service.api;

import pl.lodz.p.edu.exception.ConflictException;
import pl.lodz.p.edu.exception.IllegalModificationException;
import pl.lodz.p.edu.DTO.users.AdminDTO;
import pl.lodz.p.edu.DTO.users.ClientDTO;
import pl.lodz.p.edu.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.model.users.Admin;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.model.users.Employee;
import pl.lodz.p.edu.model.users.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
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
