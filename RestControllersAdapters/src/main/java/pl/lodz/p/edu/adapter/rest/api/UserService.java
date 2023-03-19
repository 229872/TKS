package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.UserInputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.core.domain.model.users.UserType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserInputDTO> getAllUsersOfType(UserType type);
    List<UserInputDTO> getAll();
    UserInputDTO get(UUID uuid) throws ObjectNotFoundRestException;

    UserInputDTO getByLogin(String login) throws ObjectNotFoundRestException;

    void activateUser(UUID entityId) throws ObjectNotFoundRestException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundRestException;

    UserInputDTO registerUser(UserInputDTO userInputDTO) throws RestConflictException;

    EmployeeInputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    ClientInputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    AdminInputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;
}
