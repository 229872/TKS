package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.EquipmentController;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.Comparator;
import java.util.List;

@Named
@RequestScoped
public class EquipmentListBean {
    @Inject
    private EquipmentController equipmentController;

    public EquipmentListBean() {}

    @PostConstruct
    public void initEquipments() {
        equipment = equipmentController.getAll();
        equipment.sort(Comparator.comparing(Equipment::getName));
        available = equipmentController.getAvailable();
        available.sort(Comparator.comparing(Equipment::getName));
    }

    private List<Equipment> equipment;

    private List<Equipment> available;

    public List<Equipment> getAvailable() { return available; }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void delete(Equipment equipment) {
        equipmentController.delete(equipment);
    }
}
