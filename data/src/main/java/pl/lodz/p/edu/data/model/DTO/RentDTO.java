package pl.lodz.p.edu.data.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RentDTO {

    private String uuid;

    @NotEmpty
    private String equipmentUUID;

    @NotEmpty
    private String clientUUID;

    @NotEmpty
    private String beginTime;

    private String endTime;

    public RentDTO() {}

    public RentDTO(String rentId, String clientUUID, String equipmentUUID,
                   String beginTime, String endTime) {
        this.uuid = rentId;
        this.clientUUID = clientUUID;
        this.equipmentUUID = equipmentUUID;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public RentDTO(String clientUUID, String equipmentUUID, String beginTime, String endTime) {
        this(null, clientUUID, equipmentUUID, beginTime, endTime);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
