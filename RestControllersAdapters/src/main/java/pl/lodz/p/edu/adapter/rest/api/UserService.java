package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.UserDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.core.domain.model.users.Employee;
import pl.lodz.p.edu.core.domain.model.users.UserType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAllUsersOfType(UserType type);
    List<UserDTO> getAll();
    UserDTO get(UUID uuid) throws ObjectNotFoundRestException;

    UserDTO getByLogin(String login) throws ObjectNotFoundRestException;

    void activateUser(UUID entityId) throws ObjectNotFoundRestException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundRestException;

    UserDTO registerUser(UserDTO userDTO) throws RestConflictException;

    EmployeeDTO updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    ClientDTO updateClient(UUID entityId, ClientDTO clientDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    AdminDTO updateAdmin(UUID entityId, AdminDTO adminDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;
}
