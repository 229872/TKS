package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.controller.EquipmentController;
import pl.lodz.p.edu.mvc.controller.RentController;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class EquipmentBean extends AbstractBean {
    @Inject
    private EquipmentController equipmentController;

    @Inject
    private RentController rentController;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String isRented = "";

    public String getIsRented() {
        return isRented;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    private Equipment equipment;

    private List<MvcRentDTO> equipmentRents;

    public List<MvcRentDTO> getEquipmentRents() {
        return equipmentRents;
    }

    @PostConstruct
    public void init() {
        String equipmentId = getUuidFromParam();
        if (equipmentId == null) {
            equipment = new Equipment();
            equipmentRents = new ArrayList<>();
        } else {
            equipment = equipmentController.get(equipmentId);
//            equipmentRents = rentController.getEquipmentRents(equipmentId);
        }
    }
    
    public void update() {
        EquipmentDTO updatedEquipment = equipmentController.update(
                equipment.getEntityId().toString(), new EquipmentDTO(equipment));
        equipment.merge(updatedEquipment);
    }

    public void create() {
        equipment = equipmentController.create(new EquipmentDTO(equipment));
    }

    public void delete() {
        int status = equipmentController.delete(equipment);
        if (status == 409) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages");

            this.isRented = resourceBundle.getString("equipment.this")
                    +" " + this.equipment.getEntityId().toString() + " " +
                    resourceBundle.getString("equipment.noDeleteRent");
        }
    }

}
