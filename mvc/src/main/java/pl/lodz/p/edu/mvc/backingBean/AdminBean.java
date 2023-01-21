package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.mvc.controller.AdminController;
import pl.lodz.p.edu.mvc.controller.RentController;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named
@RequestScoped
public class AdminBean extends AbstractBean {

    @Inject
    private AdminController adminController;

    @Inject
    private RentController rentController;

    private Admin admin;
    private String ifMatch = "";

    public AdminBean() {
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getIfMatch() {
        return ifMatch;
    }

    //    private List<MvcRentDTO> clientRents;
//
//    public List<MvcRentDTO> getClientRents() {
//        return clientRents;
//    }

    public void setIfMatch(String ifMatch) {
        this.ifMatch = ifMatch;
    }

    @PostConstruct
    public void init() {
        String clientId = getUuidFromParam();
        if (clientId == null) {
            admin = new Admin();
        } else {
            try {
                HttpResponse<String> response = adminController.getWithRequest(clientId);
                admin = om.readValue(response.body(), Admin.class);

                if (response.headers().firstValue("ETag").isEmpty()) {
                    throw new RuntimeException("no eTag header");
                }
                String eTag = response.headers().firstValue("ETag").get();

                ifMatch = om.readValue(eTag, String.class);

            } catch (IOException e) {
                throw new RuntimeException(e); // todo komunikat
            }

        }
    }

    public void update() {
        AdminDTO updatedAdmin = adminController.update(
                admin.getEntityId().toString(), new AdminDTO(admin), ifMatch);
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
