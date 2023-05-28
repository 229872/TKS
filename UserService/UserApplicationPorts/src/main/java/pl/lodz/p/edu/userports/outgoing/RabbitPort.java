package pl.lodz.p.edu.userports.outgoing;


import pl.lodz.p.edu.core.domain.other.ClientCreateEvent;
import pl.lodz.p.edu.core.domain.other.ClientUpdateEvent;

public interface RabbitPort {
    void produce(ClientCreateEvent event);
    void produce(ClientUpdateEvent event);
}
