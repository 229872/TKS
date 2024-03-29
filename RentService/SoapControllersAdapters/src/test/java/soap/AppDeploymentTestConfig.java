package soap;

import org.junit.ClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;


public class AppDeploymentTestConfig {

    public static final MountableFile WAR = MountableFile
            .forHostPath(Paths.get("target/SoapControllersAdapters-1.0-SNAPSHOT.war").toAbsolutePath());

    public static Network network = Network.newNetwork();

    private static final Logger LOGGER =
            LoggerFactory.getLogger("Docker-Container");

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
//            .withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("Service"))
            .withCopyFileToContainer(WAR, "/opt/payara/deployments/SoapControllersAdapters-1.0-SNAPSHOT.war")
            .waitingFor(Wait.forHttp("/soap/EquipmentSoapControllerService"))
            .withReuse(true);



    protected static String baseUrl = "";

    public static void init() {
        baseUrl = String.format("http://%s:%d/soap/EquipmentSoapControllerService", payara.getHost(), payara.getMappedPort(8080));
    }
}