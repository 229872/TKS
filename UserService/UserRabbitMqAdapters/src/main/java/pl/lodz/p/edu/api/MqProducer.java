package pl.lodz.p.edu.api;

public interface MqProducer<T> {
    void produce(T event);
}
