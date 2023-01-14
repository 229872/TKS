package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.mvc.controller.AdminController;
import pl.lodz.p.edu.mvc.controller.RentController;

@Named
@RequestScoped
public class AdminBean extends AbstractBean {

    @Inject
    private AdminController adminController;

    @Inject
    private RentController rentController;

    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

//    private List<MvcRentDTO> clientRents;
//
//    public List<MvcRentDTO> getClientRents() {
//        return clientRents;
//    }

    public AdminBean() {}

    @PostConstruct
    public void init() {
        String clientId = getUuidFromParam();
        if (clientId == null) {
            admin = new Admin();
//            clientRents = new ArrayList<>();
        } else {
            admin = adminController.get(clientId);
//            clientRents = rentController.getClientRents(clientId);
        }
    }

    public void update() {
        AdminDTO updatedAdmin = adminController.update(
                admin.getEntityId().toString(), new AdminDTO(admin));
        admin.merge(updatedAdmin);
    }

    public void create() {
        admin = adminController.create(new AdminDTO(admin));
        admin.setPassword("");
    }


    public void activate() {
        adminController.activate(admin.getEntityId().toString());
        admin = adminController.get(admin.getEntityId().toString());
    }

    public void deactivate() {
        adminController.deactivate(admin.getEntityId().toString());
        admin = adminController.get(admin.getEntityId().toString());
    }
}
