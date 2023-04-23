package pl.lodz.p.edu.usersoap.api;

import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;
import pl.lodz.p.edu.usersoap.dto.input.AdminInputDTO;
import pl.lodz.p.edu.usersoap.dto.input.ClientInputDTO;
import pl.lodz.p.edu.usersoap.dto.input.EmployeeInputDTO;
import pl.lodz.p.edu.usersoap.dto.input.UserInputDTO;
import pl.lodz.p.edu.usersoap.dto.output.AdminOutputDTO;
import pl.lodz.p.edu.usersoap.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.usersoap.dto.output.EmployeeOutputDTO;
import pl.lodz.p.edu.usersoap.dto.output.UserOutputDTO;
import pl.lodz.p.edu.usersoap.exception.ObjectNotFoundSoapException;
import pl.lodz.p.edu.usersoap.exception.SoapConflictException;
import pl.lodz.p.edu.usersoap.exception.SoapIllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface UserSoapService {
    List<UserOutputDTO> getAllUsersOfType(UserType type);
    List<UserOutputDTO> getAll();
    UserOutputDTO get(UUID uuid) throws ObjectNotFoundSoapException;

    UserOutputDTO getByLogin(String login) throws ObjectNotFoundSoapException;

    void activateUser(UUID entityId) throws ObjectNotFoundSoapException;

    void deactivateUser(UUID entityId) throws ObjectNotFoundSoapException;

    UserOutputDTO registerUser(UserInputDTO userInputDTO) throws SoapConflictException;

    EmployeeOutputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException;

    ClientOutputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException;

    AdminOutputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException;
}
