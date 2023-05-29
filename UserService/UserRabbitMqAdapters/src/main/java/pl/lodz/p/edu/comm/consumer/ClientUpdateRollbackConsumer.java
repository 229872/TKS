package pl.lodz.p.edu.comm.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.edu.data.ClientRollbackEvent;
import pl.lodz.p.edu.ProducerUser;
import pl.lodz.p.edu.data.ClientUpdateRollbackEvent;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Client;
import pl.lodz.p.edu.userports.incoming.UserServicePort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Log
public class ClientUpdateRollbackConsumer  {

  @Inject
  @ProducerUser
  private Channel channel;

  @Inject
  @ConfigProperty(name = "mq.queue.client.rollback.update", defaultValue = "CLIENT_ROLLBACK_QUEUE_UPDATE")
  private String queueName;

  @Inject
  private UserServicePort userServicePort;

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
    ClientUpdateRollbackEvent clientUpdateRollbackEvent;
    try (Jsonb jsonb = JsonbBuilder.create()) {
      clientUpdateRollbackEvent = jsonb.fromJson(message, ClientUpdateRollbackEvent.class);
      userServicePort.updateClient(clientUpdateRollbackEvent.getLogin(), clientUpdateRollbackEvent.getName(),
              clientUpdateRollbackEvent.getLastName());

    } catch (ObjectNotFoundServiceException ignored) {
      log.warning("Error during updating an User");
    } catch (Exception e) {
      log.warning("Invalid message format");
    }
  }
}
