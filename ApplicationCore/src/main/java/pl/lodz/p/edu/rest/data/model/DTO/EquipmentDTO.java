package pl.lodz.p.edu.rest.data.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import pl.lodz.p.edu.rest.data.model.Equipment;

public class EquipmentDTO {

    @NotEmpty
    private String name;

    @Positive
    private double bail;

    @Positive
    private double firstDayCost;

    @Positive
    private double nextDaysCost;

    private String description;

    public EquipmentDTO() {}

    public EquipmentDTO(Equipment e) {
        name = e.getName();
        bail = e.getBail();
        firstDayCost = e.getFirstDayCost();
        nextDaysCost = e.getNextDaysCost();
        description = e.getDescription();
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" +
                "name='" + name + '\'' +
                ", bail=" + bail +
                ", firstDayCost=" + firstDayCost +
                ", nextDaysCost=" + nextDaysCost +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBail() {
        return bail;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public void setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public void setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
