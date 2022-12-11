package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.ClientController;

import javax.faces.bean.SessionScoped;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class ClientListBean {

    @Inject
    private ClientController clientController;

    public ClientListBean() {}

    @PostConstruct
    public void initClients() {
        clients = clientController.getAll();
        clients.sort(Comparator.comparing(User::getLogin));
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }


    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void search() {
        clients = clientController.search(searchParam);
    }
}
