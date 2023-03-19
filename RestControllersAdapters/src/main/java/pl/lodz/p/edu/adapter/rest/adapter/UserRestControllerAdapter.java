package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.UserInputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.users.*;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

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
    public List<UserInputDTO> getAllUsersOfType(UserType type) {
        return servicePort.getAllUsersOfType(type).stream()
                .map(this::convertToDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInputDTO> getAll() {
        return servicePort.getAll().stream()
                .map(this::convertToDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserInputDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            User user = servicePort.get(uuid);
            return convertToDTOFactory(user);

        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserInputDTO getByLogin(String login) throws ObjectNotFoundRestException {
        try {
            User user = servicePort.get(login);
            return convertToDTOFactory(user);
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
    public UserInputDTO registerUser(UserInputDTO userInputDTO) throws RestConflictException {
        User user = convertToDomainModelFactory(userInputDTO);
        try {
            User registeredUser = servicePort.registerUser(user);
            return convertToDTOFactory(registeredUser);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public EmployeeInputDTO updateEmployee(UUID entityId, EmployeeInputDTO employeeDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(employeeDTO);
        try {
            Employee employee = servicePort.updateEmployee(entityId, (Employee) user);
            return (EmployeeInputDTO) convertToDTOFactory(employee);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public ClientInputDTO updateClient(UUID entityId, ClientInputDTO clientDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(clientDTO);
        try {
            Client client = servicePort.updateClient(entityId, (Client) user);
            return (ClientInputDTO) convertToDTOFactory(client);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public AdminInputDTO updateAdmin(UUID entityId, AdminInputDTO adminDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(adminDTO);
        try {
            Admin admin = servicePort.updateAdmin(entityId, (Admin) user);
            return (AdminInputDTO) convertToDTOFactory(admin);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    private User convertToDomainModelFactory(UserInputDTO userInputDTO) {
        return switch (userInputDTO.getUserType()) {
            case ADMIN -> toDomainMapper.convertAdminToDomainModel((AdminInputDTO) userInputDTO);
            case EMPLOYEE -> toDomainMapper.convertEmployeeToDomainModel((EmployeeInputDTO) userInputDTO);
            case CLIENT -> toDomainMapper.convertClientToDomainModel((ClientInputDTO) userInputDTO);
        };
    }

    private UserInputDTO convertToDTOFactory(User user) {
        return switch (user.getUserType()) {
            case ADMIN -> toDTOMapper.convertAdminToAdminDTO((Admin) user);
            case EMPLOYEE -> toDTOMapper.convertEmployeeToEmployeeDTO((Employee) user);
            case CLIENT -> toDTOMapper.convertClientToClientDTO((Client) user);
        };
    }
}
