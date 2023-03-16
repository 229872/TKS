package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.UserDTO;
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
    public List<UserDTO> getAllUsersOfType(UserType type) {
        return servicePort.getAllUsersOfType(type).stream()
                .map(this::convertToDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAll() {
        return servicePort.getAll().stream()
                .map(this::convertToDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            User user = servicePort.get(uuid);
            return convertToDTOFactory(user);

        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserDTO getByLogin(String login) throws ObjectNotFoundRestException {
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
    public UserDTO registerUser(UserDTO userDTO) throws RestConflictException {
        User user = convertToDomainModelFactory(userDTO);
        try {
            User registeredUser = servicePort.registerUser(user);
            return convertToDTOFactory(registeredUser);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public EmployeeDTO updateEmployee(UUID entityId, EmployeeDTO employeeDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(employeeDTO);
        try {
            Employee employee = servicePort.updateEmployee(entityId, (Employee) user);
            return (EmployeeDTO) convertToDTOFactory(employee);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public ClientDTO updateClient(UUID entityId, ClientDTO clientDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(clientDTO);
        try {
            Client client = servicePort.updateClient(entityId, (Client) user);
            return (ClientDTO) convertToDTOFactory(client);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    @Override
    public AdminDTO updateAdmin(UUID entityId, AdminDTO adminDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        User user = convertToDomainModelFactory(adminDTO);
        try {
            Admin admin = servicePort.updateAdmin(entityId, (Admin) user);
            return (AdminDTO) convertToDTOFactory(admin);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }
    private User convertToDomainModelFactory(UserDTO userDTO) {
        return switch (userDTO.getUserType()) {
            case ADMIN -> toDomainMapper.convertAdminToDomainModel((AdminDTO) userDTO);
            case EMPLOYEE -> toDomainMapper.convertEmployeeToDomainModel((EmployeeDTO) userDTO);
            case CLIENT -> toDomainMapper.convertClientToDomainModel((ClientDTO) userDTO);
        };
    }

    private UserDTO convertToDTOFactory(User user) {
        return switch (user.getUserType()) {
            case ADMIN -> toDTOMapper.convertAdminToAdminDTO((Admin) user);
            case EMPLOYEE -> toDTOMapper.convertEmployeeToEmployeeDTO((Employee) user);
            case CLIENT -> toDTOMapper.convertClientToClientDTO((Client) user);
        };
    }
}
