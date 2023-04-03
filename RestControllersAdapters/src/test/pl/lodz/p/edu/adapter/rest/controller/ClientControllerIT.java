package pl.lodz.p.edu.adapter.rest.controller;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class ClientControllerIT extends AppDeploymentTestConfig {

    ObjectMapper obj = new ObjectMapper();
    ClientInputDTO validClient;
    String validClientStr;

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validClient = DataFakerRestControllerInputDTO.getClient();
        validClientStr = obj.writeValueAsString(validClient);
    }

//    @Test
//    void loop() {
//        System.out.println("8080: " + payara.getMappedPort(8080));
//        getUUIDOfNewObject();
//
//        while(true);
//    }

    // create
    @Test
    void createClient_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post(baseUrl + "clients")
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
                .post(baseUrl + "clients")
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
                .post(baseUrl + "clients")
                .then()
                .statusCode(417);
    }

    @Test
    void createClient_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post(baseUrl + "clients")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post(baseUrl + "clients")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllClients_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneClient_byUUID_correct()  {
        String uuid = getUUIDOfNewObject();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(200);


        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(200)
                .body("userId", equalTo(uuid));
    }

    @Test
    void getOneClient_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneClient_byLogin_correct()  {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post(baseUrl + "clients")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/login/" + login)
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
                .get(baseUrl + "clients/login/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyClientsByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFakerRestControllerInputDTO.randStr(30);
        validClient.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validClient);
        List<ClientOutputDTO> initialOutputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("", ClientOutputDTO.class);
        int initialSize = initialOutputDTOList.size();

        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post(baseUrl + "clients")
                .then()
                .statusCode(201);

        validClient.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post(baseUrl + "clients")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(initialSize + 2));
    }

    // update
    @Test
    void updateOneClient_correct() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();
        System.out.println(uuid);

        String newFirstName = "___other_first_name___";
        validClient.setFirstName(newFirstName);
        String updatedClientStr = obj.writeValueAsString(validClient);
        System.out.println(updatedClientStr);
        given()
                .header("Content-Type", "application/json")
                .body(updatedClientStr)
                .when()
                .put(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(200);
        String firstName = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
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
                .put(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneClient_updateLogin() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();

        validClient.setLogin("__other_login__");
        String updatedLogin = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateOneClient_illegalValues() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();

        validClient.setAddress(null);
        String updatedLogin = obj.writeValueAsString(validClient);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "clients/" + uuid)
                .then()
                .statusCode(417);
    }

    @Test
    void activateDeactivateTest() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();

        // deactivate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put(baseUrl + "clients/" + uuid + "/deactivate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
                .then()
                .body("active", equalTo(false));

        // activate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put(baseUrl + "clients/" + uuid + "/activate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients/" + uuid)
                .then()
                .body("active", equalTo(true));
    }


    private String getUUIDOfNewObject() {
        given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post(baseUrl + "clients")
                .then()
                .statusCode(201);

        List<ClientOutputDTO> outputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "clients")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("", ClientOutputDTO.class);

        return outputDTOList.get(0).getUserId().toString();
    }
}
