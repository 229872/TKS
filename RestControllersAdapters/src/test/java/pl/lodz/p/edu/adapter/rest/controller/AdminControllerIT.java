package pl.lodz.p.edu.adapter.rest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@Testcontainers
public class AdminControllerIT extends AppDeploymentTestConfig {

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @Test
    public void listClients() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseUrl + "admins")
                .then()
                .statusCode(200);
    }
}
