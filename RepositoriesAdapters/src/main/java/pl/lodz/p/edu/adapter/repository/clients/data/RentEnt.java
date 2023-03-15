package pl.lodz.p.edu.adapter.repository.clients.data;

import lombok.*;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQuery(name = RentEnt.FIND_ALL, query = "SELECT rent FROM RentEnt rent")
@NamedQuery(name = RentEnt.FIND_BY_ID, query = "SELECT rent FROM RentEnt rent WHERE rent.id = :id")
@NamedQuery(name = RentEnt.FIND_BY_EQUIPMENT, query = "SELECT rent FROM RentEnt rent WHERE rent.equipmentEnt = :equipment")
@NamedQuery(name = RentEnt.FIND_BY_CLIENT, query = "SELECT rent FROM RentEnt rent WHERE rent.clientEnt = :client")
public class RentEnt extends AbstractEntity {
    public static final String FIND_ALL = "RentEnt.findAll";
    public static final String FIND_BY_ID = "RentEnt.findById";
    public static final String FIND_BY_EQUIPMENT = "RentEnt.findByEquipment";
    public static final String FIND_BY_CLIENT = "RentEnt.findByClient";

    @JoinColumn(name = "equipment_id")
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private EquipmentEnt equipmentEnt;

    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ClientEnt clientEnt;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
