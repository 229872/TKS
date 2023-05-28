package pl.lodz.p.edu.comm;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.ProducerUser;
import pl.lodz.p.edu.api.MqProducer;
import pl.lodz.p.edu.ClientCreatedEvent;

import java.io.IOException;

@ApplicationScoped
@Log
public class ClientCreatedProducer implements MqProducer<ClientCreatedEvent> {

    @Inject
    @ProducerUser
    private Channel channel;

    @Inject
    @ConfigProperty(name = "mq.queue.client.create", defaultValue = "CLIENT_CREATED_QUEUE")
    private String queueName;

    @Inject
    @ConfigProperty(name = "mq.exchange.client", defaultValue = "CLIENT_EXCHANGE")
    private String exchangeName;

    public void produce(ClientCreatedEvent event) {
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
            log.info("rabbitmq publishing message: '"
                    + message + "' on channel "
                    + queueName + " with exchange " + exchangeName);
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
