package pl.lodz.p.edu.mvc.backingBean;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.controller.ClientController;

import javax.faces.bean.SessionScoped;
import java.util.List;

@Named
@SessionScoped
public class ClientListBean {

    @Inject
    private ClientController clientController;

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public ClientListBean() {
        clients = clientController.getAll();
    }
}
