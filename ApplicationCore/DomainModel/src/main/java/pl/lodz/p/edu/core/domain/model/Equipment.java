package pl.lodz.p.edu.core.domain.model;


public class Equipment extends AbstractModelData {

    private Long id;


    private String name;

    private double bail;


    private double firstDayCost;

    private double nextDaysCost;

    private String description;


    public Equipment(double firstDayCost, double nextDaysCost, double bail, String name) {
        this.firstDayCost = firstDayCost;
        this.nextDaysCost = nextDaysCost;
        this.bail = bail;
        this.name = name;
        this.description = null;
    }


    public void merge(Equipment equipment) {
        this.name = equipment.getName();
        this.bail = equipment.getBail();
        this.firstDayCost = equipment.getFirstDayCost();
        this.nextDaysCost = equipment.getNextDaysCost();
        this.description = equipment.getDescription();
    }

    public Equipment() {}

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
