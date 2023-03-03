package clients.data;

import clients.data.users.ClientEnt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
@Access(AccessType.FIELD)
public class RentEnt extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 0, name = "rent_sequence_generator")
    @Column(name = "rent_id")
    private long id;

    @NotNull
    @JoinColumn(name = "equipment_id")
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private EquipmentEnt equipmentEnt;

    @NotNull
    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ClientEnt client;

    @NotNull
    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    public RentEnt(LocalDateTime beginTime, LocalDateTime endTime,
                   EquipmentEnt equipmentEnt, ClientEnt client) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.equipmentEnt = equipmentEnt;
        this.client = client;
    }

    public RentEnt() {}

    public RentEnt(long id, LocalDateTime beginTime, LocalDateTime endTime,
                   EquipmentEnt equipmentEnt, ClientEnt client) {

        this(beginTime, endTime, equipmentEnt, client);
        this.id = id;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("id=").append(id);
        sb.append("Klient=").append(getClient().toString());
        sb.append("Sprzęt=").append(getEquipment().toString());
        sb.append("Czas wypożyczenia=");
        sb.append("Początek=").append(beginTime);
        sb.append(" do ");
        sb.append("Koniec=").append(endTime);
        sb.append('}');
        return sb.toString();
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public EquipmentEnt getEquipment() {
        return equipmentEnt;
    }

    public void setEquipment(EquipmentEnt equipmentEnt) {
        this.equipmentEnt = equipmentEnt;
    }

    public ClientEnt getClient() {
        return client;
    }

    public void setClient(ClientEnt client) {
        this.client = client;
    }
}
