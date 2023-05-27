package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.core.domain.model.Client;

@ApplicationScoped
public class UserFromDomainToDTOMapper {


    public ClientOutputDTO convertClientToClientOutputDTO(Client client) {
        return new ClientOutputDTO(
                client.getEntityId(),
                client.getName(),
                client.getLastName()
        );
    }
}
