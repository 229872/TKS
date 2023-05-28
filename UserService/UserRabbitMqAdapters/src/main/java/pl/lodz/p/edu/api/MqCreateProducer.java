package pl.lodz.p.edu.api;

import pl.lodz.p.edu.data.ClientCreatedEvent;

public interface MqCreateProducer {
  void produce(ClientCreatedEvent createdEvent);
}
