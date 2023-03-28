package pl.lodz.p.edu.adapter.rest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

@Testcontainers
class AppDeploymentTestConfig {

    public static final MountableFile WAR = MountableFile
            .forHostPath(Paths.get("target/RestControllersAdapters-1.0-SNAPSHOT.war").toAbsolutePath());

    public static Network network = Network.newNetwork();

    @Container
    public static GenericContainer database = new PostgreSQLContainer("postgres:14")
            .withDatabaseName("nbddb")
            .withUsername("nbd")
            .withPassword("nbdpassword")
            .withNetwork(network)
            .withNetworkAliases("db")
            .withReuse(true);

    @Container
    public static GenericContainer payara = new GenericContainer("payara/server-full:6.2023.2-jdk17")
            .withExposedPorts(8080)
            .dependsOn(database)
            .withNetwork(network)
            .withNetworkAliases("payara")
            .withCopyFileToContainer(WAR, "/opt/payara/deployments/RestControllersAdapters-1.0-SNAPSHOT.war")
            .waitingFor(Wait.forHttp("/rest/api/clients"))
            .withReuse(true);

    protected static String baseUrl = "";

    public static void init() {
        baseUrl = String.format("http://%s:%d/rest/api/", payara.getHost(), payara.getMappedPort(8080));
    }
}