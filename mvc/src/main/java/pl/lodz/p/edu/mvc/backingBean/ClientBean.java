package pl.lodz.p.edu.mvc.backingBean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.controller.ClientController;

@Named
@RequestScoped
public class ClientBean extends AbstractBean {

    @Inject
    private ClientController clientController;

    private Client client;

    public Client getClient() {
        return client;
    }

    // metody z kontrolera: wszystkie oprócz search i getAll
    // niewiadomo

    public ClientBean() {
        String clientId = getUuidFromParam();
        if(clientId == null) {
            redirect("manageClients.xhtml");
            return;
        }
        client = clientController.get(clientId);
    }

    public void update() {
        ClientDTO updatedClient = clientController.update(
                client.getEntityId().toString(), new ClientDTO(client));
        client.merge(updatedClient);
    }

    public void create() {
        ClientDTO createdClient = clientController.create(new ClientDTO(client));
        client.merge(createdClient);
    }

    public void activate() {
        clientController.activate(client.getEntityId().toString());
        client = clientController.get(client.getEntityId().toString());
    }

    public void deactivate() {
        clientController.deactivate(client.getEntityId().toString());
        client = clientController.get(client.getEntityId().toString());
    }
}
