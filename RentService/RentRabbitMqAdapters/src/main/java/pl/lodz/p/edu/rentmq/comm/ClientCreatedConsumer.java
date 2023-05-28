package pl.lodz.p.edu.rentmq.comm;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.core.domain.exception.IllegalDateException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.rentmq.event.ClientCreatedEvent;
import pl.lodz.p.edu.rentmq.event.ClientRollbackEvent;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

@ApplicationScoped
@Log
public class ClientCreatedConsumer {
    @Inject
    @ProducerRent
    private Channel channel;

    @PostConstruct
    public void showState() {
        log.log(Level.INFO, "ClientCreatedConsumer created ------------------------------------------");
    }

    @Inject
    private UserServicePort port;

    @Inject
    private ClientRollbackProducer producer;

    @Inject
    @ConfigProperty(name = "mq.queue.client.create", defaultValue = "CLIENT_CREATED_QUEUE")
    private String queueName;

    private void initConsumer(@Observes Startup event) {
        if (channel == null) {
            log.warning("Error during initializing consumer, connection is not established");
            return;
        }
        log.info("Init consumer method");

        try {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicConsume(queueName, true, this::deliverCallback, consumerTag -> { });
            log.info("rabbitmq rent channel initialized");
        } catch (IOException ignored) {
            log.warning("Error during connecting to queue");
        }
    }

    void deliverCallback(String consumerTag, Delivery delivery) {
        log.info("begin delivery");
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        log.info("rabbitmq message clientCreated got. Body: '" + message + "'");
        ClientCreatedEvent clientCreatedEvent = new ClientCreatedEvent();

        try (Jsonb jsonb = JsonbBuilder.create()) {
            clientCreatedEvent = jsonb.fromJson(message, ClientCreatedEvent.class);

            port.registerUser(new Client(null, clientCreatedEvent.getLogin(),
                    clientCreatedEvent.getFirstName(), clientCreatedEvent.getLastName()));

        } catch (IllegalDateException e) {
            log.warning("Error during creating an User");
            producer.produce(new ClientRollbackEvent(clientCreatedEvent.getLogin()));
        } catch (Exception e) {
            log.warning("ClientCreatedConsumer: Invalid message format");
            producer.produce(new ClientRollbackEvent(clientCreatedEvent.getLogin()));
        }
    }
}
