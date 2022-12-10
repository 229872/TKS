package pl.lodz.p.edu.data.model.DTO;

import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.users.Client;

import java.time.LocalDateTime;

public class MvcRentDTO {

    private String entityId;

    private Equipment equipment;

    private Client client;

    private String beginTime;

    private String endTime;

    private boolean active;

    public MvcRentDTO(String entityId, Equipment equipment, Client client,
                      String beginTime, String endTime, boolean active) {
        this.entityId = entityId;
        this.equipment = equipment;
        this.client = client;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.active = active;
    }

    public MvcRentDTO() {
    }

    public Rent toRent() {
        LocalDateTime beginTime = LocalDateTime.parse(this.beginTime);
        LocalDateTime endTime = LocalDateTime.parse(this.endTime);
        return new Rent(beginTime, endTime, equipment, client);
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
