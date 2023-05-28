package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import pl.lodz.p.edu.core.domain.model.Client;

@ApplicationScoped
public class UserFromDomainToDataMapper {

    public ClientEnt convertClientToDataModelCreate(Client client) {
        return new ClientEnt(
                client.getLogin(),
                client.getName(),
                client.getLastName()
        );
    }


    public ClientEnt convertPUTClientToDataModel(Client client) {
        return new ClientEnt(
                client.getEntityId(),
                client.getLogin(),
                client.getName(),
                client.getLastName()
        );
    }

}
