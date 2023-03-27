package pl.lodz.p.edu.adapter.rest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static io.restassured.RestAssured.*;

@Testcontainers
class ClientControllerIT {

    public static final MountableFile WAR = MountableFile.forHostPath("target/RestControllerAdapters-1.0-SNAPSHOT.war");

    public static Network network = Network.newNetwork();

    @Container
    public static PostgreSQLContainer database = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14")
            .withNetwork(network)
            .withNetworkAliases("postgres");

    @Container
    static GenericContainer payara = new GenericContainer(DockerImageName.parse("payara/micro:5.2022.1-jdk11"))
            .withExposedPorts(8080)
            .dependsOn(database)
            .withNetwork(network)
            .withCopyFileToContainer(WAR, "/opt/payara/deployments/app.war")
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .withEnv("DB_JDBC_URL", String.format("jdbc:postgresql://localhost:5432/%s", "nbddb"))
            .withEnv("DB_USER", "nbd")
            .withEnv("DB_PASSWORD", "nbdpassword")
            .withCommand("--deploy /opt/payara/deployments/app.war --noCluster --contextRoot /");


    @Test
    public void testContainersRunning() {
        Assertions.assertTrue(payara.isRunning());
    }



//    public static ClientController clientController;

//    @Test
//    public void testGetAllClients() {
//        Response result = clientController.getAll();
//        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
//    }

    @Test
    public void listClients() {
        System.out.println(payara.getHost());
        System.out.println(payara.getEnvMap());
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://" + payara.getHost() + ":" + payara.getMappedPort(8080) + "/clients")
                .then()
                .assertThat().statusCode(200);
//                .and()
//                .body()
    }
}