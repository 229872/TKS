package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.RentDTO;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.controller.RentController;

@Named
@RequestScoped
public class RentBean extends AbstractBean {

//    @Inject
//    private ClientController clientController;
//
//    @Inject
//    private EquipmentController equipmentController;

    @Inject
    private RentController rentController;

//    private Client client;
//
//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
//
//    private Equipment equipment;
//
//    public Equipment getEquipment() {
//        return equipment;
//    }
//
//    public void setEquipment(Equipment equipment) {
//        this.equipment = equipment;
//    }

    private Rent rent;

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public RentBean() {}

    @PostConstruct
    public void init() {
        String rentId = getUuidFromParam();
        if (rentId == null) {
            rent = new Rent();
        } else {
            rent = rentController.get(rentId);
        }
    }

    public void update() {
        MvcRentDTO updatedRent = rentController.update(
                rent.getEntityId().toString(), new MvcRentDTO(rent.getEntityId().toString(),
                        rent.getEquipment(), rent.getClient(), rent.getBeginTime().toString(),
                        rent.getEndTime().toString(), rent.getClient().isActive()));
        rent.merge(updatedRent.toRent());
    }

    public void create() {
        rent = rentController.create(new MvcRentDTO(rent.getClient().getEntityId().toString(),
                rent.getEquipment(), rent.getClient(), rent.getBeginTime().toString(),
                rent.getEndTime().toString(), rent.getClient().isActive()));
    }

}
