package pl.lodz.p.edu.comm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.Getter;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.event.ClientRollbackEvent;
import pl.lodz.p.edu.event.ClientRollbackUpdateEvent;
import pl.lodz.p.edu.event.ClientUpdateEvent;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Getter
@Log
public class ClientUpdatedConsumer {

    @Inject
    private Channel channel;

    @Inject
    private UserServicePort port;

    @Inject
    private ClientRollbackProducer producer;

    @Inject
    @ConfigProperty(name = "mq.queue.client.update")
    private String queueName;

    private void initConsumer(@Observes Startup event) {
        if (channel == null) {
            log.warning("Error during initializing consumer, connection is not established");
            return;
        }

        try {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicConsume(queueName, true, this::deliverCallback, consumerTag -> {
            });
        } catch (IOException ignored) {
            log.warning("Error during connecting to queue");
        }
    }

    private void deliverCallback(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ClientUpdateEvent clientUpdateEvent = new ClientUpdateEvent();

        try (Jsonb jsonb = JsonbBuilder.create()) {
            clientUpdateEvent = jsonb.fromJson(message, ClientUpdateEvent.class);

            port.updateClient(clientUpdateEvent.getId(),
                    new Client(clientUpdateEvent.getId(),
                            clientUpdateEvent.getFirstName(), clientUpdateEvent.getLastName()));

        } catch (ObjectNotFoundServiceException ignored) {
            log.warning("Error during updating an User");
            producer.produce(new ClientRollbackUpdateEvent(clientUpdateEvent.getBackup()));
        } catch (Exception e) {
            log.warning("Invalid message format");
            producer.produce(new ClientRollbackUpdateEvent(clientUpdateEvent.getBackup()));
        }
    }
}
