package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.AdminController;
import pl.lodz.p.edu.mvc.controller.ClientController;

import javax.faces.bean.SessionScoped;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class AdminListBean {

    @Inject
    private AdminController adminController;

    public AdminListBean() {}

    @PostConstruct
    public void initAdmins() {
        admins = adminController.getAll();
        admins.sort(Comparator.comparing(User::getLogin));
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }


    private List<Admin> admins;

    public List<Admin> getAdmins() {
        return admins;
    }


    public void search() {
        admins = adminController.search(searchParam);
    }
}
