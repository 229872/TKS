package pl.lodz.p.edu.adapter.rest.users.api;


import pl.lodz.p.edu.adapter.rest.users.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.UserInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.EmployeeOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.UserOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestIllegalModificationException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserOutputDTO> getAllUsersOfType(UserType type);
    List<UserOutputDTO> getAll();
    UserOutputDTO get(UUID uuid) throws ObjectNotFoundRestException;

    UserOutputDTO getByLogin(String login) throws ObjectNotFoundRestException;

    void activateUser(UUID entityId) throws ObjectNotFoundRestException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundRestException;

    UserOutputDTO registerUser(UserInputDTO userInputDTO) throws RestConflictException;

    EmployeeOutputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    ClientOutputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    AdminOutputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;
}
