package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.controller.EquipmentController;

import javax.faces.bean.SessionScoped;
import java.util.List;

@Named
@SessionScoped
public class EquipmentListBean {
    @Inject
    private EquipmentController equipmentController;

    public EquipmentListBean() {}

    @PostConstruct
    public void initClients() {
        equipment = equipmentController.getAll();
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    private List<Equipment> equipment;

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void delete(Equipment equipment) {
        equipmentController.delete(equipment);
    }
}
