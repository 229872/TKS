package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.controller.ClientController;
import pl.lodz.p.edu.mvc.controller.RentController;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ClientBean extends AbstractBean {

    @Inject
    private ClientController clientController;

    @Inject
    private RentController rentController;

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private List<MvcRentDTO> clientRents;

    public List<MvcRentDTO> getClientRents() {
        return clientRents;
    }

    public ClientBean() {}

    @PostConstruct
    public void init() {
        String clientId = getUuidFromParam();
        if (clientId == null) {
            client = new Client();
            clientRents = new ArrayList<>();
        } else {
            client = clientController.get(clientId);
            clientRents = rentController.getClientRents(clientId);
        }
    }

    public void update() {
        ClientDTO updatedClient = clientController.update(
                client.getEntityId().toString(), new ClientDTO(client));
        client.merge(updatedClient);
    }

    public void create() {
        client = clientController.create(new ClientDTO(client));
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
