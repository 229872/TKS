package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.controller.ClientController;
import pl.lodz.p.edu.mvc.controller.EquipmentController;
import pl.lodz.p.edu.mvc.controller.RentController;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class RentBean extends AbstractBean {

    @Inject
    private ClientController clientController;

    @Inject
    private EquipmentController equipmentController;

    @Inject
    private RentController rentController;

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private Equipment equipment;

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    private MvcRentDTO rent;

    public MvcRentDTO getRent() {
        return rent;
    }

    public void setRent(MvcRentDTO rent) {
        this.rent = rent;
    }

    public RentBean() {}

    private String error;

    public String getError() {
        return error;
    }

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.jsf_messages");


    @PostConstruct
    public void init() {
        String rentId = getFromParam("uuidRent");
        if (rentId == null) {
            rent = new MvcRentDTO();
            String clientId = getFromParam("uuidClient");
            if (clientId == null) {
                client = new Client();
            } else {
                client = clientController.get(clientId);
            }
            rent.setClient(client);
            String equipmentId = getFromParam("uuidEquipment");
            if (equipmentId == null) {
                equipment = new Equipment();
            } else {
                equipment = equipmentController.get(equipmentId);
            }
            rent.setEquipment(equipment);
            LocalDateTime now = LocalDateTime.now();
            String nowStr = now.format(DateTimeFormatter.ISO_DATE);
            rent.setBeginTime(nowStr);
        } else {
            rent = rentController.get(rentId);
            client = rent.getClient();
            equipment = rent.getEquipment();
        }
    }

    public void update() {
        try {
            rent = rentController.update(rent);
        } catch (BadRequestException e) {
            error = resourceBundle.getString("rent.bad.date");
        } catch (ClientErrorException e) {
            error = resourceBundle.getString("rent.conflict");
        }

    }

    public void create() {
        rent.setBeginTime(rent.getBeginTime() + "T00:00:00.000");
        if(!rent.getEndTime().isEmpty()) {
            rent.setEndTime(rent.getEndTime() + "T00:00:00.000");
        } else {
            rent.setEndTime(null);
        }
        try {
            rent = rentController.create(rent);
        } catch (BadRequestException e) {
            error = resourceBundle.getString("rent.bad.date");
            return;
        } catch (ClientErrorException e) {
            error = resourceBundle.getString("rent.conflict");
            return;
        }



        rent.setBeginTime(rent.getBeginTime().replaceAll("T.*$", ""));
        if(rent.getEndTime() != null) {
            rent.setEndTime(rent.getEndTime().replaceAll("T.*$", ""));
        }
    }

    public void delete() {
        rentController.delete(rent.getEntityId());
    }
}
