package pl.lodz.p.edu.data.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.data.model.Rent;

import java.util.UUID;

public class RentDTO {

    @NotEmpty
    private String equipmentUUID;

    @NotEmpty
    private String clientUUID;

    @NotEmpty
    private String beginTime;

    private String endTime;

    public RentDTO() {
    }

    public RentDTO(String clientUUID, String equipmentUUID,
                   String beginTime, String endTime) {
        this.clientUUID = clientUUID;
        this.equipmentUUID = equipmentUUID;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getEquipmentUUID() {
        return equipmentUUID;
    }

    public String getClientUUID() {
        return clientUUID;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public UUID getClientUUIDFromString() {
        return UUID.fromString(clientUUID);
    }

    public UUID getEquipmentUUIDFromString() {
        return UUID.fromString(equipmentUUID);
    }

    public void setEquipmentUUID(String equipmentUUID) {
        this.equipmentUUID = equipmentUUID;
    }

    public void setClientUUID(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "RentDTO{" +
                "equipmentUUID='" + equipmentUUID + '\'' +
                ", clientUUID='" + clientUUID + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
