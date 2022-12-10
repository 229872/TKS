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

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class EquipmentBean extends AbstractBean {
    @Inject
    private EquipmentController equipmentController;

    @Inject
    private RentController rentController;

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

}
