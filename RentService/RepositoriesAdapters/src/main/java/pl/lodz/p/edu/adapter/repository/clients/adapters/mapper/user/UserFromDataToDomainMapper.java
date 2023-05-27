package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.core.domain.model.Client;

@ApplicationScoped
public class UserFromDataToDomainMapper {
    public Client convertClientToDomainModel(ClientEnt clientEnt) {
        return new Client(
                clientEnt.getEntityId(),
                clientEnt.getFirstName(),
                clientEnt.getLastName()
        );
    }
}
