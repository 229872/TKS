package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.controller.RentController;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.List;

@Named
@RequestScoped
public class ClientRentsBean {
    @Inject
    private RentController rentController;

    public ClientRentsBean() {}

    @PostConstruct
    public void initClients() {
        clientRents = rentController.getAll();
    }

    private List<MvcRentDTO> clientRents;

    public List<MvcRentDTO> getClientRents() {
        return clientRents;
    }

    public void delete(MvcRentDTO rentDTO) {
        rentController.delete(rentDTO.getEntityId());
        clientRents = rentController.getAll();
    }
}
