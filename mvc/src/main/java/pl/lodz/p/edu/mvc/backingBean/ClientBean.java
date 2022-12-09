package pl.lodz.p.edu.mvc.backingBean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
        client = clientController.get(getUuidFromParam());
    }
}
