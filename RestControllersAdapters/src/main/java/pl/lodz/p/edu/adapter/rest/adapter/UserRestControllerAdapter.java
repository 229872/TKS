package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.UserDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;
import pl.lodz.p.edu.core.domain.model.users.User;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserRestControllerAdapter implements UserService {

    @Inject
    private UserServicePort servicePort;

    @Inject
    private UserFromDTOToDomainMapper toDomainMapper;

    @Inject
    private UserFromDomainToDTOMapper toDTOMapper;

    @Override
    public List<UserDTO> searchOfType(String type, String login) {
        return servicePort.searchOfType(type, login).stream()
                .map(this::convertToDTOFactory)
                .toList();
    }

    @Override
    public List<UserDTO> getAllUsersOfType(String type) {
        return servicePort.getAllUsersOfType(type).stream()
                .map(this::convertToDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByUuidOfType(String type, UUID entityId) {
        User user = servicePort.getUserByUuidOfType(type, entityId);
        return convertToDTOFactory(user);
    }

    @Override
    public UserDTO getUserByLoginOfType(String type, String login) {
        User user = servicePort.getUserByLoginOfType(type, login);
        return convertToDTOFactory(user);
    }

    @Override
    public void activateUser(String type, UUID entityId) {
        servicePort.activateUser(type, entityId);
    }

    @Override
    public void deactivateUser(String type, UUID entityId) {
        servicePort.deactivateUser(type, entityId);
    }

    //TODO change all methods to one with user instead employee, client, admin
    @Override
    public void registerEmployee(EmployeeDTO employee) throws RestConflictException {
        User user = convertToDomainModelFactory(employee, "EMPLOYEE");
        try {
            servicePort.registerEmployee((Employee) user);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws RestIllegalModificationException {
        User user = convertToDomainModelFactory(employeeDTO, "EMPLOYEE");
        try {
            servicePort.updateEmployee(entityId,(Employee) user);
        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void registerClient(ClientDTO clientDTO) throws RestConflictException {
        User user = convertToDomainModelFactory(clientDTO, "CLIENT");
        try {
            servicePort.registerClient((Client) user);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateClient(UUID entityId, ClientDTO clientDTO) throws RestIllegalModificationException {
        User user = convertToDomainModelFactory(clientDTO, "CLIENT");
        try {
            servicePort.updateClient(entityId,(Client) user);
        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void registerAdmin(AdminDTO adminDTO) throws RestConflictException {
        User user = convertToDomainModelFactory(adminDTO, "ADMIN");
        try {
            servicePort.registerAdmin((Admin) user);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateAdmin(UUID entityId, AdminDTO adminDTO) throws RestIllegalModificationException {
        User user = convertToDomainModelFactory(adminDTO, "ADMIN");
        try {
            servicePort.updateAdmin(entityId,(Admin) user);
        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        }
    }

    //fixme change string to enum
    private User convertToDomainModelFactory(UserDTO userDTO, String userType) {
        return switch (userType) {
            case "ADMIN" -> toDomainMapper.convertAdminToDomainModel((AdminDTO) userDTO);
            case "EMPLOYEE" -> toDomainMapper.convertEmployeeToDomainModel((EmployeeDTO) userDTO);
            case "CLIENT" -> toDomainMapper.convertClientToDomainModel((ClientDTO) userDTO);
            default -> throw new IllegalArgumentException();
        };
    }

    private UserDTO convertToDTOFactory(User user) {
        return switch (user.getUserType()) {
            case "ADMIN" -> toDTOMapper.convertAdminToAdminDTO((Admin) user);
            case "EMPLOYEE" -> toDTOMapper.convertEmployeeToEmployeeDTO((Employee) user);
            case "CLIENT" -> toDTOMapper.convertClientToClientDTO((Client) user);
            default -> throw new IllegalArgumentException();
        };
    }
}
