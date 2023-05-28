package pl.lodz.p.edu.adapter.rest.heathcheck;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Liveness
@ApplicationScoped
public class StartupCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("rest-rent");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:8080/rent/api/clients").openConnection();
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
