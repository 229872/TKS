package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;

@MicroShedTest
@SharedContainerConfig(AppDeploymentConfig.class)
class ClientControllerIT {

    @RESTClient
    public static ClientController clientController;

    @Test
    public void testGetAllClients() {
        Response result = clientController.getAll();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }
}