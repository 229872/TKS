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

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    private String searchParam;

    public List<Client> getClients() {
        return clients;
    }

    public ClientListBean() {
        clients = clientController.getAll();
    }

    public void search() {
        clients = clientController.search(searchParam);
    }
}
