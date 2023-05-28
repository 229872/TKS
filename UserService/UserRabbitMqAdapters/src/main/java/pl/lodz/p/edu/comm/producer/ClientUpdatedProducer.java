package pl.lodz.p.edu.comm.producer;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.Getter;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.data.ClientUpdatedEvent;
import pl.lodz.p.edu.ProducerUser;
import pl.lodz.p.edu.api.MqUpdateProducer;

import java.io.IOException;

@ApplicationScoped
@Getter
@Log
public class ClientUpdatedProducer implements MqUpdateProducer {

    @Inject
    @ProducerUser
    private Channel channel;

    @Inject
    @ConfigProperty(name = "mq.queue.client.update", defaultValue = "CLIENT_UPDATE_QUEUE")
    private String queueName;

    @Inject
    @ConfigProperty(name = "mq.exchange.client", defaultValue = "CLIENT_EXCHANGE")
    private String exchangeName;

    public void produce(ClientUpdatedEvent event) {
        if (channel == null) {
            log.warning("Error while initializing consumer, connection not established");
            return;
        }

        String message;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            message = jsonb.toJson(event);
        } catch (Exception e) {
            log.warning("Invalid message format");
            return;
        }

        try {
            channel.basicPublish(exchangeName, queueName, null, message.getBytes());
        } catch (IOException e) {
            log.warning("Error while producing message, connection not established");
        }
    }

    private void initProducer(@Observes Startup event) {
        if (channel == null) {
            log.warning("Error while initializing producer, connection not established");
            return;
        }

        try {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, queueName);
        } catch (IOException ignored) {
            log.warning("Error while connecting to queue");
        }
    }
}
