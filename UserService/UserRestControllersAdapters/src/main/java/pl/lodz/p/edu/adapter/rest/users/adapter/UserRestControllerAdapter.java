package pl.lodz.p.edu.adapter.rest.users.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.users.adapter.mapper.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.users.adapter.mapper.UserFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.users.api.UserService;
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
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ConflictException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.IllegalModificationException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.*;
import pl.lodz.p.edu.userports.incoming.UserServicePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRestControllerAdapter implements UserService {

    @Inject
    private UserServicePort servicePort;

    @Inject
    private UserFromDTOToDomainMapper toDomainMapper;

    @Inject
    private UserFromDomainToDTOMapper toDTOMapper;

    @Override
    public List<UserOutputDTO> getAllUsersOfType(UserType type) {
        return servicePort.getAllUsersOfType(type).stream()
                .map(this::convertToOutputDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOutputDTO> getAll() {
        return servicePort.getAll().stream()
                .map(this::convertToOutputDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutputDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            User user = servicePort.get(uuid);
            return convertToOutputDTOFactory(user);

        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserOutputDTO getByLogin(String login) throws ObjectNotFoundRestException {
        try {
            User user = servicePort.get(login);
            return convertToOutputDTOFactory(user);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void activateUser(UUID entityId) throws ObjectNotFoundRestException {
        try {
            servicePort.activateUser(entityId);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deactivateUser(UUID entityId) throws ObjectNotFoundRestException {
        try {
            servicePort.deactivateUser(entityId);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public UserOutputDTO registerUser(UserInputDTO userInputDTO) throws RestConflictException {
        User user = convertToDomainModelFactory(userInputDTO);
        try {
            User registeredUser = servicePort.registerUser(user);
            return convertToOutputDTOFactory(registeredUser);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public EmployeeOutputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(employeeDTO);
        try {
            Employee employee = servicePort.updateEmployee(entityId, (Employee) user);
            return (EmployeeOutputDTO) convertToOutputDTOFactory(employee);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public ClientOutputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(clientDTO);
        try {
            Client client = servicePort.updateClient(entityId, (Client) user);
            return (ClientOutputDTO) convertToOutputDTOFactory(client);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public AdminOutputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(adminDTO);
        try {
            Admin admin = servicePort.updateAdmin(entityId, (Admin) user);
            return (AdminOutputDTO) convertToOutputDTOFactory(admin);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    private User convertToDomainModelFactory(UserInputDTO userInputDTO) {
        return switch (userInputDTO.getUserType()) {
            case ADMIN -> toDomainMapper.convertInputAdminToDomainModel((AdminInputDTO) userInputDTO);
            case EMPLOYEE -> toDomainMapper.convertInputEmployeeToDomainModel((EmployeeInputDTO) userInputDTO);
            case CLIENT -> toDomainMapper.convertInputClientToDomainModel((ClientInputDTO) userInputDTO);
        };
    }

    private UserOutputDTO convertToOutputDTOFactory(User user) {
        return switch (user.getUserType()) {
            case ADMIN -> toDTOMapper.convertAdminToAdminOutputDTO((Admin) user);
            case EMPLOYEE -> toDTOMapper.convertEmployeeToEmployeeOutputDTO((Employee) user);
            case CLIENT -> toDTOMapper.convertClientToClientOutputDTO((Client) user);
        };
    }
}
