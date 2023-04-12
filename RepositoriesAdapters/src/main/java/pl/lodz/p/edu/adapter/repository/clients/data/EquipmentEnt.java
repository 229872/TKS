package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "equipment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = EquipmentEnt.FIND_ALL, query = "SELECT equipment FROM EquipmentEnt equipment")
@NamedQuery(name = EquipmentEnt.FIND_BY_ID, query = "SELECT equipment FROM EquipmentEnt equipment WHERE equipment.entityId = :id")
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

    public EquipmentEnt(UUID entityId, double firstDayCost, double nextDaysCost, double bail, String name) {
        this.entityId = entityId;
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }
}
