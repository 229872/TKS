package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.ClientController;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class ClientListBean implements Serializable {

    @Inject
    private ClientController clientController;
    private String searchParam;
    private List<Client> clients;

    public ClientListBean() {
    }

    @PostConstruct
    public void initClients() {
        clients = clientController.getAll();
        clients.sort(Comparator.comparing(User::getLogin));
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void search() {
        clients = clientController.search(searchParam);
    }
}
