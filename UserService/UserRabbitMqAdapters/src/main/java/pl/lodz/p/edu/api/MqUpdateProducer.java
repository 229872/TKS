package pl.lodz.p.edu.api;

import pl.lodz.p.edu.data.ClientUpdatedEvent;

public interface MqUpdateProducer {
  void produce(ClientUpdatedEvent event);
}
