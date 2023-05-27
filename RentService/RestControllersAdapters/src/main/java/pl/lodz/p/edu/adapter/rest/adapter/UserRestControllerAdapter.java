package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRestControllerAdapter implements UserService {

    @Inject
    private UserServicePort servicePort;

    @Inject
    private UserFromDomainToDTOMapper toDTOMapper;

    @Override
    public List<ClientOutputDTO> getAll() {
        return servicePort.getAll().stream()
                .map(this::convertToOutputDTOFactory)
                .collect(Collectors.toList());
    }

    @Override
    public ClientOutputDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            Client user = servicePort.get(uuid);
            return toDTOMapper.convertClientToClientOutputDTO(user);

        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    private ClientOutputDTO convertToOutputDTOFactory(Client user) {
        return toDTOMapper.convertClientToClientOutputDTO(user);
    }
}
