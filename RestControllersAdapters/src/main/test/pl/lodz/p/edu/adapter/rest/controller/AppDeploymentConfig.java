package pl.lodz.p.edu.adapter.rest.controller;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

public class AppDeploymentConfig implements SharedContainerConfiguration {

    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/app")
            .withReadinessPath("/health/ready");
}
