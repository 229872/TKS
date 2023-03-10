package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "equipment")
@Access(AccessType.FIELD)
public class EquipmentEnt extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 0, name = "equipment_sequence_generator")
    @Column(name = "equipment_id", updatable = false)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "bail")
    @Positive
    private double bail;

    @Column(name = "first_day_cost")
    @Positive
    private double firstDayCost;

    @Column(name = "next_day_cost")
    @Positive
    private double nextDaysCost;

    @Column(name = "description")
    private String description;


    public EquipmentEnt(double firstDayCost, double nextDaysCost, double bail, String name) {
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }
    public EquipmentEnt() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("-------------------- Equipment{");
        sb.append("id=").append(id);
        sb.append(", firstDayCost=").append(firstDayCost);
        sb.append(", nextDaysCost=").append(nextDaysCost);
        sb.append(", bail=").append(bail);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }


    public Long getId() {
        return id;
    }

    public double getFirstDayCost() {
        return firstDayCost;
    }

    public double getNextDaysCost() {
        return nextDaysCost;
    }

    public double getBail() {
        return bail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setFirstDayCost(double firstDayCost) {
        this.firstDayCost = firstDayCost;
    }

    public void setNextDaysCost(double nextDaysCost) {
        this.nextDaysCost = nextDaysCost;
    }

    public void setBail(double bail) {
        this.bail = bail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
