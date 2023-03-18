package pl.lodz.p.edu.core.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Equipment extends AbstractModelData {

    private String name;
    private double bail;
    private double firstDayCost;
    private double nextDaysCost;
    private String description;


    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name) {
        super(null);
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }

    public Equipment(UUID id, double firstDayCost, double nextDaysCost, double bail, String name) {
        super(id);
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }


    public void update(Equipment equipment) {
        this.name = equipment.getName();
        this.bail = equipment.getBail();
        this.firstDayCost = equipment.getFirstDayCost();
        this.nextDaysCost = equipment.getNextDaysCost();
        this.description = equipment.getDescription();
    }
}
