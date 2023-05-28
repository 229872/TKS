package pl.lodz.p.edu.adapter.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.data.ClientCreatedEvent;
import pl.lodz.p.edu.data.ClientUpdatedEvent;
import pl.lodz.p.edu.core.domain.other.ClientCreateEvent;
import pl.lodz.p.edu.core.domain.other.ClientUpdateEvent;

@ApplicationScoped
public class ClientEventMapper {

    public ClientCreateEvent mapToClientEvent(ClientCreatedEvent clientCreatedEvent) {
        return new ClientCreateEvent(
                clientCreatedEvent.getLogin(),
                clientCreatedEvent.getFirstName(),
                clientCreatedEvent.getLastName()
        );
    }

    public ClientCreatedEvent mapToCreatedEvent(ClientCreateEvent clientCreateEvent) {
        return new ClientCreatedEvent(
                clientCreateEvent.getLogin(),
                clientCreateEvent.getFirstName(),
                clientCreateEvent.getLastName()
        );
    }

    public ClientUpdatedEvent mapToUpdatedEvent(ClientUpdateEvent updateEvent) {
        return new ClientUpdatedEvent(
                updateEvent.getLogin(),
                updateEvent.getFirstName(),
                updateEvent.getLastName(),
                updateEvent.getFirstNameBackup(),
                updateEvent.getLastNameBackup()
        );
    }
}
