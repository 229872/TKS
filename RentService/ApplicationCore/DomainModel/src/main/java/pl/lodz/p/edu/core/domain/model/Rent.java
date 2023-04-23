package pl.lodz.p.edu.core.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.users.Client;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Rent extends AbstractModelData {


    private Equipment equipment;
    private Client client;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;


    public Rent(LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client) {
        super(null);
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.equipment = equipment;
        this.client = client;
    }

    public Rent(UUID id, LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client) {
        super(id);
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.equipment = equipment;
        this.client = client;
    }


    public void update(Rent rent, Equipment equipment, Client client) {
        this.beginTime = rent.getBeginTime();
        this.endTime = rent.getEndTime();
        this.equipment = equipment;
        this.client = client;
    }

    public void validateTime(LocalDateTime time) throws ObjectNotValidException {
        if (this.endTime != null && this.beginTime.isAfter(this.endTime)) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
        if (this.beginTime.isBefore(time)) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
    }
}
