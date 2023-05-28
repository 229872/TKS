package pl.lodz.p.edu.adapter.repository.users.healthcheck;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class DatabaseCheck implements HealthCheck {

    @PersistenceContext(name = "app2")
    private EntityManager em;


    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("database-user");
        try {
            if(em.isOpen()) {
                responseBuilder.withData("connection-open", true).up();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            responseBuilder.down()
                    .withData("connection-open", false);
        }
        return responseBuilder.build();
    }
}