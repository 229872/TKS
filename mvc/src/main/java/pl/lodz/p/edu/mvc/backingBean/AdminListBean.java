package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.AdminController;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Named
@RequestScoped
public class AdminListBean implements Serializable {

    @Inject
    private AdminController adminController;
    private String searchParam;
    private List<Admin> admins;

    public AdminListBean() {
    }

    @PostConstruct
    public void initAdmins() {
        admins = adminController.getAll();
        admins.sort(Comparator.comparing(User::getLogin));
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public List<Admin> getAdmins() {
        return admins;
    }


    public void search() {
        admins = adminController.search(searchParam);
    }
}
