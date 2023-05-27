package pl.lodz.p.edu.userports.outgoing;


public interface RabbitPort<T> {
    void produce(T event);
}
