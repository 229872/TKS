package pl.lodz.p.edu.adapter.rest.users.healthcheck;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Liveness
@ApplicationScoped
public class EndpointCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("rest-user");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:8080/user/api/clients").openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            builder.withData("response-code", responseCode);
            if (responseCode == 200) {
                builder.up();
            } else {
                builder.down();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.build();
    }
}
