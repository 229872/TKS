package pl.lodz.p.edu.rentmq.comm;

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
import pl.lodz.p.edu.rentmq.event.ClientRollbackBaseEvent;

import java.io.IOException;

@ApplicationScoped
@Log
public class ClientRollbackProducer {

    @Inject
    @ProducerRent
    private Channel channel;

    @Inject
    @ConfigProperty(name = "mq.queue.client.rollback", defaultValue = "CLIENT_ROLLBACK_QUEUE")
    private String queueName;

    @Inject
    @ConfigProperty(name = "mq.exchange.client", defaultValue = "CLIENT_EXCHANGE")
    private String exchangeName;

    public void produce(ClientRollbackBaseEvent event) {
        if (channel == null) {
            log.warning("ClientRollbackProducer: Error while initializing consumer, connection not established");
            return;
        }

        String message;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            message = jsonb.toJson(event);
        } catch (Exception e) {
            log.warning("ClientRollbackProducer: Invalid message format");
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
