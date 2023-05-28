package pl.lodz.p.edu.adapter.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.ClientCreatedEvent;
import pl.lodz.p.edu.user.core.domain.usermodel.other.ClientEvent;

@ApplicationScoped
public class ClientEventMapper {

    public ClientEvent mapToClientEvent(ClientCreatedEvent clientCreatedEvent) {
        return new ClientEvent(
                clientCreatedEvent.getLogin(),
                clientCreatedEvent.getFirstName(),
                clientCreatedEvent.getLastName()
        );
    }

    public ClientCreatedEvent mapToCreatedEvent(ClientEvent clientEvent) {
        return new ClientCreatedEvent(
                clientEvent.getLogin(),
                clientEvent.getFirstName(),
                clientEvent.getLastName()
        );
    }
}
