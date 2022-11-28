package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

public class RentControllerTest {

    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    RentDTO validRent;
    RentDTO unvalidRentData;
    RentDTO unvalidRentLogic;
    String clientId;
    String equipmentId;
    String validRentStr;
    String unvalidRentDataStr;
    String unvalidRentLogicStr;
    Client validClient;
    Equipment validEquipment;


    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validClient = DataFaker.getClient();
        validEquipment = DataFaker.getEquipment();
        String validClientStr = obj.writeValueAsString(validClient);
        clientId = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String validEquipmentStr = obj.writeValueAsString(validEquipment);
        equipmentId = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        validRent = new RentDTO(clientId, equipmentId,
                "2023-04-05T12:38:35.585", "2023-05-05T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        unvalidRentData = new RentDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", equipmentId,
                "2023-06-06T12:38:35.585", "2024-07-07T12:38:35.585");
        unvalidRentDataStr = obj.writeValueAsString(unvalidRentData);
        unvalidRentLogic = new RentDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", equipmentId,
                "2024-06-06T12:38:35.585", "2023-07-07T12:38:35.585");
        unvalidRentLogicStr = obj.writeValueAsString(unvalidRentLogic);
    }


    // =============================================== create
    @Test
    void createRent_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);
    }

    @Test
    void createRent_correctUnfinished() throws JsonProcessingException {
        validRent.setEndTime(null);
        String unfinishedRent = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(unfinishedRent)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);
    }

    @Test
    void createRent_clientNotExist() throws JsonProcessingException {
        validRent.setClientUUID(UUID.randomUUID().toString());
        String body = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_equipmentNotExist() throws JsonProcessingException {
        validRent.setEquipmentUUID(UUID.randomUUID().toString());
        String body = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_invalidDate() throws JsonProcessingException {
        validRent.setBeginTime("invalid date");
        String body = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_dateBeforeToday() throws JsonProcessingException {
        validRent.setBeginTime("1999-04-05T12:38:35.585");
        String body = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_createSecondAfterFirstEnded() throws JsonProcessingException {
        RentDTO first = new RentDTO(clientId, equipmentId, "2023-01-01T12:00:00.000", "2023-01-02T12:00:00.000");
        String firstStr = obj.writeValueAsString(first);
        given()
                .header("Content-Type", "application/json")
                .body(firstStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);

        RentDTO second = new RentDTO(clientId, equipmentId, "2023-01-02T12:00:01.000", null);
        String secondStr = obj.writeValueAsString(second);
        given()
                .header("Content-Type", "application/json")
                .body(secondStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);
    }

    @Test
    void createRent_createSecondBeforeFirstEnded() throws JsonProcessingException {
        RentDTO first = new RentDTO(clientId, equipmentId, "2023-01-01T12:00:00.000", null);
        String firstStr = obj.writeValueAsString(first);
        given()
                .header("Content-Type", "application/json")
                .body(firstStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);

        RentDTO second = new RentDTO(clientId, equipmentId, "2023-01-02T12:00:01.000", "2023-01-04T12:00:00.000");
        String secondStr = obj.writeValueAsString(second);
        given()
                .header("Content-Type", "application/json")
                .body(secondStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(409);
    }

    // =============================================== read

    // get all
    @Test
    void getAllRents() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents")
                .then()
                .statusCode(200);
    }

    @Test
    void getAllClientRents_someClientRents() {
        given()
                .header("Content-Type", "application/json")
                .body(validRent)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents/client/" + clientId)
                .then()
                .statusCode(200)
                .body("size()", equalTo(1));
    }

    @Test
    void getAllClientRents_noClientRents() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents/client/" + clientId)
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void getAllClientRents_clientNotExists() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents/client/" + UUID.randomUUID().toString())
                .then()
                .statusCode(404);
    }

    @Test
    void getOneRent_byUUID_correct()  {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .extract().path("entityId");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", equalTo(uuid));
    }

    @Test
    void getOneRent_byUUID_notExist()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/rents/" + uuid)
                .then()
                .statusCode(404);
    }

    // =============================================== update

    @Test
    void updateRent_correct() throws JsonProcessingException {
        validRent.setBeginTime("2023-04-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        validRent.setEndTime("2023-05-05T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put("/rents/" + uuid)
                .then()
                .statusCode(200);
    }

    @Test
    void updateRent_updateBeforeBeginTime() throws JsonProcessingException {
        validRent.setBeginTime("2023-05-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        validRent.setEndTime("2023-05-04T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put("/rents/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateRent_clientNotExist() throws JsonProcessingException {
        validRent.setBeginTime("2023-05-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        validRent.setClientUUID(UUID.randomUUID().toString());
        validRentStr = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put("/rents/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateRent_incorrect_BadRequest() {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRent)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        
        given()
                .header("Content-Type", "application/json")
                .body(unvalidRentData)
                .when()
                .put("/rents/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void deleteRent_correct() throws JsonProcessingException {
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRent)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/rents/" + uuid )
                .then()
                .statusCode(204);
    }


    @Test
    void deleteRent_incorrect() {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validRent)
                .when()
                .post("/rents")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/rents/" + uuid )
                .then()
                .statusCode(409);
    }

    @Test
    void deleteRent_RentNotExists() throws JsonProcessingException {
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/rents" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

}
