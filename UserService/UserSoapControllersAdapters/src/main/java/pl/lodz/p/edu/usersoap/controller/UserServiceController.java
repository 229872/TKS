package pl.lodz.p.edu.usersoap.controller;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import pl.lodz.p.edu.user.core.domain.usermodel.users.UserType;
import pl.lodz.p.edu.usersoap.api.UserSoapService;
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

@WebService
public class UserServiceController {

    @Inject
    private UserSoapService userService;

    public List<UserOutputDTO> getAllUsersOfType(UserType type) {
        return userService.getAllUsersOfType(type);
    }

    public List<UserOutputDTO> getAll() {
        return userService.getAll();
    }

    public UserOutputDTO get(UUID uuid) throws ObjectNotFoundSoapException {
        return userService.get(uuid);
    }

    public UserOutputDTO getByLogin(String login) throws ObjectNotFoundSoapException {
        return userService.getByLogin(login);
    }

    public void activateUser(UUID entityId) throws ObjectNotFoundSoapException {
        userService.activateUser(entityId);
    }

    public void deactivateUser(UUID entityId) throws ObjectNotFoundSoapException {
        userService.deactivateUser(entityId);
    }

    public UserOutputDTO registerUser(UserInputDTO userInputDTO) throws SoapConflictException {
        return userService.registerUser(userInputDTO);
    }

    public EmployeeOutputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException {
        return userService.updateEmployee(entityId, employeeDTO);
    }

    public ClientOutputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException {
        return userService.updateClient(entityId, clientDTO);
    }

    public AdminOutputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException {
        return userService.updateAdmin(entityId, adminDTO);
    }
}
