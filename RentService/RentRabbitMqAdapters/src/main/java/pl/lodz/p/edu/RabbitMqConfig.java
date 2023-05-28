package pl.lodz.p.edu;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@Log
public class RabbitMqConfig {
    private Connection connection;

    @Inject
    @ConfigProperty(name = "mq.port", defaultValue = "5672")
    private Integer port;

    @Inject
    @ConfigProperty(name = "mq.host", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "mq.username", defaultValue = "nbd")
    private String userName;

    @Inject
    @ConfigProperty(name = "mq.password", defaultValue = "nbdpassword")
    private String password;

    @Produces
    public Channel createChannel() throws IOException {
        if (connection == null) {
            throw new IOException("Cannot get channel. Connection with RabbitMQ is not established");
        }

        log.info("rabbitmq rent before channel create");
        return connection.createChannel();
    }


    @PostConstruct
    void afterCreate() {
        log.info("rabbitmq rent after create");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);

        try {
            connection = connectionFactory.newConnection();
            log.info("rabbitmq rent connection created");
        } catch (IOException | TimeoutException e) {
            log.warning("Connection with RabbitMQ could not be established");
        }
    }

    @PreDestroy
    void beforeDestroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ignored) {
                log.warning("Error during closing connection with RabbitMQ");
            }
        }
    }
}