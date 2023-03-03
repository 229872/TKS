package pl.lodz.p.edu.DTO;

import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.model.Rent;
import pl.lodz.p.edu.model.users.Client;

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

    public MvcRentDTO(Equipment equipment, Client client,
                      String beginTime, String endTime) {
        this(null, equipment, client, beginTime, endTime, true);
    }

    public MvcRentDTO() {
    }

    public Rent toRent() {
        LocalDateTime beginTime = LocalDateTime.parse(this.beginTime);
        LocalDateTime endTime = null;
        if(this.endTime.isEmpty()) {
            endTime = LocalDateTime.parse(this.endTime);
        }
        return new Rent(beginTime, endTime, equipment, client);
    }

    public RentDTO toRentDTO() {
        return new RentDTO(client.getEntityId().toString(), equipment.getEntityId().toString(), beginTime, endTime);
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
