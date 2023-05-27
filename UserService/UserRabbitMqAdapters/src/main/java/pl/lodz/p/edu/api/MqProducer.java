package pl.lodz.p.edu.api;

import pl.lodz.p.edu.ClientCreatedEvent;

public interface MqProducer<T> {
    void produce(T event);
}
