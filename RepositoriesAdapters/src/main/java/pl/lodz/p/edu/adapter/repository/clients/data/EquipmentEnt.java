package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = EquipmentEnt.FIND_ALL, query = "SELECT equipment FROM EquipmentEnt equipment")
@NamedQuery(name = EquipmentEnt.FIND_BY_ID, query = "SELECT equipment FROM EquipmentEnt equipment WHERE equipment.id = :id")
public class EquipmentEnt extends AbstractEntity {
    public static final String FIND_ALL = "Equipment.findAll";
    public static final String FIND_BY_ID = "Equipment.findById";
    private String name;
    private double bail;
    @Column(name = "first_day_cost")
    private double firstDayCost;
    @Column(name = "next_day_cost")
    private double nextDaysCost;
    private String description;


    public EquipmentEnt(double firstDayCost, double nextDaysCost, double bail, String name) {
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }
}
