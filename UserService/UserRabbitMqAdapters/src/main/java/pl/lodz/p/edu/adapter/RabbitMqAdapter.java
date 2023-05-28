package pl.lodz.p.edu.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.data.ClientUpdatedEvent;
import pl.lodz.p.edu.adapter.mapper.ClientEventMapper;
import pl.lodz.p.edu.api.MqCreateProducer;
import pl.lodz.p.edu.data.ClientCreatedEvent;
import pl.lodz.p.edu.api.MqUpdateProducer;
import pl.lodz.p.edu.core.domain.other.ClientCreateEvent;
import pl.lodz.p.edu.core.domain.other.ClientUpdateEvent;
import pl.lodz.p.edu.userports.outgoing.RabbitPort;

@ApplicationScoped
public class RabbitMqAdapter implements RabbitPort {

    @Inject
    private ClientEventMapper clientEventMapper;

    @Inject
    private MqUpdateProducer updateProducer;

    @Inject
    private MqCreateProducer createProducer;

    @Override
    public void produce(ClientCreateEvent event) {
        ClientCreatedEvent createdEvent = clientEventMapper.mapToCreatedEvent(event);
        createProducer.produce(createdEvent);
    }

    @Override
    public void produce(ClientUpdateEvent event) {
        ClientUpdatedEvent updatedEvent = clientEventMapper.mapToUpdatedEvent(event);
        updateProducer.produce(updatedEvent);
    }
}
