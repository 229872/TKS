package pl.lodz.p.edu.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.mapper.ClientEventMapper;
import pl.lodz.p.edu.api.MqProducer;
import pl.lodz.p.edu.ClientCreatedEvent;
import pl.lodz.p.edu.user.core.domain.usermodel.other.ClientEvent;
import pl.lodz.p.edu.userports.outgoing.RabbitPort;

@ApplicationScoped
public class RabbitMqAdapter implements RabbitPort<ClientEvent> {

    @Inject
    private ClientEventMapper clientEventMapper;

    @Inject
    private MqProducer<ClientCreatedEvent> producer;

    @Override
    public void produce(ClientEvent event) {
        ClientCreatedEvent createdEvent = clientEventMapper.mapToCreatedEvent(event);
        producer.produce(createdEvent);
    }
}
