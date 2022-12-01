package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;

import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

class ClientControllerTest {

    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    ClientDTO validClient;
    String validClientStr;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validClient = new ClientDTO(DataFaker.getClient());
        validClientStr = obj.writeValueAsString(validClient);
    }

    // create

    @Test
    void createClient_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .body("login", equalTo(validClient.getLogin()));
    }

    @Test
    void createClient_noBody() {
        given()
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post("/clients")
                .then()
                .statusCode(400);
    }

    @Test
    void createClient_illegalValue() throws JsonProcessingException {
        validClient.setAddress(null);
        String notValidClientStr = obj.writeValueAsString(validClient);
        System.out.println(notValidClientStr);
        given()
                .header("Content-Type", "application/json")
                .body(notValidClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(400);
    }

    @Test
    void createClient_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllClients_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneClient_byUUID_correct()  {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .extract().path("entityId");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", equalTo(uuid));
    }

    @Test
    void getOneClient_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneClient_byLogin_correct()  {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/login/" + login)
                .then()
                .statusCode(200)
                .body("login", equalTo(login));
    }

    @Test
    void getOneClient_byLogin_noSuchLogin()  {
        String login = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/login/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyClientsByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFaker.randStr(30);
        validClient.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post("/clients")
                .then()
                .statusCode(201);

        validClient.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post("/clients")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    // update
    @Test
    void updateOneClient_correct() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String newFirstName = "___other_first_name___";
        validClient.setFirstName(newFirstName);
        String updatedClientStr = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(updatedClientStr)
                .when()
                .put("/clients/" + uuid)
                .then()
                .statusCode(200);
        String firstName = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/" + uuid)
                .then()
                .statusCode(200)
                .extract().path("firstName");
        assertEquals(newFirstName, firstName);
    }

    @Test
    void updateOneClient_noClient() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .put("/clients/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneClient_updateLogin() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validClient.setLogin("__other_login__");
        String updatedLogin = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/clients/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateOneClient_illegalValues() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validClient.setAddress(null);
        String updatedLogin = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/clients/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void activateDeactivateTest() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        // deactivate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/clients/" + uuid + "/deactivate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/" + uuid)
                .then()
                .body("active", equalTo(false));

        // activate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/clients/" + uuid + "/activate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/clients/" + uuid)
                .then()
                .body("active", equalTo(true));
    }
}