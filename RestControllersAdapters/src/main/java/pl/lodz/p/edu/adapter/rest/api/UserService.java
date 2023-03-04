package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.UserDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> searchOfType(String type, String login);

    List<UserDTO> getAllUsersOfType(String type);

    UserDTO getUserByUuidOfType(String type, UUID entityId);

    UserDTO getUserByLoginOfType(String type, String login);

    void activateUser(String type, UUID entityId);

    void deactivateUser(String type, UUID entityId);

    void registerEmployee(EmployeeDTO employee) throws RestConflictException;

    void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws RestIllegalModificationException;

    void registerClient(ClientDTO client) throws RestConflictException;

    void updateClient(UUID entityId, ClientDTO clientDTO) throws RestIllegalModificationException;

    void registerAdmin(AdminDTO admin) throws RestConflictException;

    void updateAdmin(UUID entityId, AdminDTO adminDTO) throws RestIllegalModificationException;
}
