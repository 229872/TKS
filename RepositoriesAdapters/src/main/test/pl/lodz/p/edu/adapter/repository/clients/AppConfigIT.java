//package pl.lodz.p.edu.adapter.repository.clients;
//
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.Network;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.containers.PostgreSQLContainerProvider;
//import org.testcontainers.containers.wait.strategy.Wait;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.MountableFile;
//import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
//import pl.lodz.p.edu.adapter.repository.clients.repo.UserRepositoryImpl;
//
//import java.sql.*;
//
//@Testcontainers
//public class AppConfigIT {
//    static Network network = Network.newNetwork();
//
//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
//            .withNetwork(network)
//            .withNetworkAliases("postgres");
//
//    @Container
//    static GenericContainer<?> jakartaPayaraMicro = new GenericContainer<>("payara/micro:6.2023.2-jdk17")
//            .withExposedPorts(8088) //fixme
//            .withCopyFileToContainer(MountableFile.forHostPath("target/RepositoriesAdapters-1.0-SNAPSHOT.jar"), "opt/payara/deployments/RepositoriesAdapters-1.0-SNAPSHOT.jar")
//            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
//            .withEnv("DB_USER", "nbd")
//            .withEnv("DB_PASSWORD", "nbdpassword")
//            .withEnv("DB_JDBC_URL", "jdbc:postgresql://localhost:5432/nbddb")
//            .withNetwork(network)
//            .dependsOn(postgreSQLContainer);
//
//    static RequestSpecification requestSpecification;
//
//    static UserRepository repository;
//
//    @BeforeAll
//    static void setup() {
//        String baseURI = "https://" + jakartaPayaraMicro.getHost() + ":" + jakartaPayaraMicro.getMappedPort(8088)
//                + "/RepositoriesAdapters-1.0-SNAPSHOT.war";
//        requestSpecification = new RequestSpecBuilder()
//                .setBaseUri(baseURI)
//                .build();
//        repository = new UserRepositoryImpl();
//        String address = postgreSQLContainer.getHost();
//        Integer port = postgreSQLContainer.getFirstMappedPort();
//
//
//    }
//
////        @Test
////        void startsUp() {
////            given(requestSpecification)
////                    .when()
////                    .get("/time")
////                    .then()
////                    .statusCode(200);
////        }
//
//    @Test
//    void repoTest() {
//        repository.getAll();
//    }
//
//    @Test
//    void areContainersWorking() {
//        Assertions.assertTrue(postgreSQLContainer.isRunning());
//        Assertions.assertTrue(jakartaPayaraMicro.isRunning());
//    }
//
////    @Test
////    void dbConnection() {
////        try(Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
////                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
////            Statement statement = connection.createStatement();
////            ResultSet resultSet = statement.
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//}
