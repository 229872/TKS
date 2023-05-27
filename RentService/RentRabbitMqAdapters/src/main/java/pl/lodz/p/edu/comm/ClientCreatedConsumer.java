package pl.lodz.p.edu.comm;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalDateException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.event.ClientCreatedEvent;
import pl.lodz.p.edu.event.ClientRollbackEvent;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Log
public class ClientCreatedConsumer {

    @Inject
    private Channel channel;

    @Inject
    private UserServicePort port;

    @Inject
    private ClientRollbackProducer producer;

    @Inject
    @ConfigProperty(name = "mq.queue.client.create")
    private String queueName;

    private void initConsumer(@Observes Startup event) {
        if (channel == null) {
            log.warning("Error during initializing consumer, connection is not established");
            return;
        }

        try {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicConsume(queueName, true, this::deliverCallback, consumerTag -> { });
        } catch (IOException ignored) {
            log.warning("Error during connecting to queue");
        }
    }

    private void deliverCallback(String consumerTag, Delivery delivery) {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ClientCreatedEvent clientCreatedEvent = new ClientCreatedEvent();

        try (Jsonb jsonb = JsonbBuilder.create()) {
            clientCreatedEvent = jsonb.fromJson(message, ClientCreatedEvent.class);

            port.registerUser(new Client(null,
                    clientCreatedEvent.getFirstName(), clientCreatedEvent.getLastName()));

        } catch (IllegalDateException e) {
            log.warning("Error during creating an User");
            producer.produce(new ClientRollbackEvent(clientCreatedEvent.getLogin()));
        } catch (Exception e) {
            log.warning("Invalid message format");
            producer.produce(new ClientRollbackEvent(clientCreatedEvent.getLogin()));
        }
    }
}
