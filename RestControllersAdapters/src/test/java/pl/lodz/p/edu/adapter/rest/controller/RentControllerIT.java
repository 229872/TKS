package pl.lodz.p.edu.adapter.rest.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.RentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;

import java.util.List;
import java.util.UUID;

@Testcontainers
public class RentControllerIT extends AppDeploymentTestConfig {

    ObjectMapper obj = new ObjectMapper();
    RentInputDTO validRent;
    RentInputDTO invalidRentData;
    RentInputDTO invalidRentLogic;
    String clientId;
    String equipmentId;
    String validRentStr;
    String invalidRentDataStr;
    String invalidRentLogicStr;
    ClientInputDTO validClient;
    EquipmentInputDTO validEquipment;

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validClient = DataFakerRestControllerInputDTO.getClient();
        validEquipment = DataFakerRestControllerInputDTO.getEquipment();

        clientId = getUUIDOfNewClient();
        equipmentId = getUUIDOfNewEquipment();

        validRent = new RentInputDTO(equipmentId, clientId,
                "2024-04-05T12:38:35.585", "2024-05-05T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        invalidRentData = new RentInputDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", clientId,
                "2024-06-06T12:38:35.585", "2025-07-07T12:38:35.585");
        invalidRentDataStr = obj.writeValueAsString(invalidRentData);
        invalidRentLogic = new RentInputDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", clientId,
                "2025-06-06T12:38:35.585", "2024-07-07T12:38:35.585");
        invalidRentLogicStr = obj.writeValueAsString(invalidRentLogic);
    }


    @Test
    void createRent_correctUnfinished() throws JsonProcessingException {
        validRent.setEndTime(null);
        String unfinishedRent = obj.writeValueAsString(validRent);

        System.out.println("\n \n");
        System.out.println(unfinishedRent);
        given()
                .header("Content-Type", "application/json")
                .body(unfinishedRent)
                .when()
                .log().all()
                .post(baseUrl + "rents")
                .then()
                .log().all()
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
                .post(baseUrl + "rents")
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
                .post(baseUrl + "rents")
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
                .post(baseUrl + "rents")
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
                .post(baseUrl + "rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_createSecondAfterFirstEnded() throws JsonProcessingException {
        RentInputDTO first = new RentInputDTO(equipmentId, clientId, "2024-01-01T12:00:00.000", "2024-01-02T12:00:00.000");
        String firstStr = obj.writeValueAsString(first);
        given()
                .header("Content-Type", "application/json")
                .body(firstStr)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(201);

        RentInputDTO second = new RentInputDTO(equipmentId, clientId, "2024-01-02T12:00:01.000", null);
        String secondStr = obj.writeValueAsString(second);
        given()
                .header("Content-Type", "application/json")
                .body(secondStr)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(201);
    }

    @Test
    void createRent_createSecondBeforeFirstEnded() throws JsonProcessingException {
        RentInputDTO first = new RentInputDTO(equipmentId, clientId, "2024-01-01T12:00:00.000", null);
        String firstStr = obj.writeValueAsString(first);
        given()
                .header("Content-Type", "application/json")
                .body(firstStr)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(201);

        RentInputDTO second = new RentInputDTO(equipmentId, clientId, "2024-01-02T12:00:01.000", "2024-01-04T12:00:00.000");
        String secondStr = obj.writeValueAsString(second);
        given()
                .header("Content-Type", "application/json")
                .body(secondStr)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(409);
    }

    // =============================================== read

    // get all
    @Test
    void getAllRents() throws JsonProcessingException {
        getUUIDOfCreatedRent(validRentStr);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void getAllClientRents_someClientRents() throws JsonProcessingException {
        String body = obj.writeValueAsString(validRent);

        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(201);

        validRent.setEquipmentUUID(getUUIDOfNewEquipment());
        body = obj.writeValueAsString(validRent);

        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(baseUrl + "rents")
                .then()
                .statusCode(201);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents/" + "client/" + clientId)
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void getAllClientRents_noClientRents() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents/" + "client/" + clientId)
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void getAllClientRents_clientNotExists() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents/" + "client/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void getOneRent_byUUID_correct() throws JsonProcessingException {
        String uuid = getUUIDOfCreatedRent(validRentStr);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents/" + uuid)
                .then()
                .statusCode(200)
                .body("rentId", equalTo(uuid));
    }

    @Test
    void getOneRent_byUUID_notExist()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents/" + uuid)
                .then()
                .statusCode(404);
    }

    // =============================================== update

    @Test
    void updateRentDate_correct() throws JsonProcessingException {
        validRent.setBeginTime("2024-04-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String rentUuid = getUUIDOfCreatedRent(validRentStr);
        System.out.println("\n" + validRentStr);

        validRent.setEndTime("2024-05-05T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);

        System.out.println("\n" + validRentStr);

        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put(baseUrl + "rents/" + rentUuid)
                .then()
                .statusCode(200);

        List<RentOutputDTO> outputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("", RentOutputDTO.class);

        Assertions.assertEquals(validRent.getBeginTime(), outputDTOList.get(outputDTOList.size() - 1).getBeginTime());
        Assertions.assertEquals(validRent.getEndTime(), outputDTOList.get(outputDTOList.size() - 1).getEndTime());
    }


    @Test
    void updateRent_updateBeforeBeginTime() throws JsonProcessingException {
        validRent.setBeginTime("2024-05-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = getUUIDOfCreatedRent(validRentStr);

        validRent.setEndTime("2024-05-04T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put(baseUrl + "rents/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateRent_clientNotExist() throws JsonProcessingException {
        validRent.setBeginTime("2023-05-05T12:38:35.585");
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = getUUIDOfCreatedRent(validRentStr);

        validRent.setClientUUID(UUID.randomUUID().toString());
        validRentStr = obj.writeValueAsString(validRent);
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .put(baseUrl + "rents/" + uuid)
                .then()
                .statusCode(400);
    }


    @Test
    void updateRent_incorrect_BadRequest() throws JsonProcessingException {
        String uuid = getUUIDOfCreatedRent(validRentStr);

        given()
                .header("Content-Type", "application/json")
                .body(invalidRentData)
                .when()
                .put(baseUrl + "rents/" + uuid)
                .then()
                .statusCode(400);
    }

    // ================================== DELETE

    @Test
    void deleteRent_correct() throws JsonProcessingException {
        validRent.setEndTime(null);
        validRentStr = obj.writeValueAsString(validRent);
        String uuid = getUUIDOfCreatedRent(validRentStr);

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseUrl + "rents/" + uuid )
                .then()
                .statusCode(204);

    }

    @Test
    void deleteRent_incorrect() throws JsonProcessingException {
        String uuid = getUUIDOfCreatedRent(validRentStr);

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseUrl + "rents/" + uuid )
                .then()
                .statusCode(409); //FIXME CONFLICT?
    }

    @Test
    void deleteRent_RentNotExists() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseUrl + "rents/" + UUID.randomUUID())
                .then()
                .statusCode(204);
    }


    /////////////////////////////////
    private String getUUIDOfNewClient() throws JsonProcessingException {
        given()
                .header("Content-Type", "application/json")
                .body(obj.writeValueAsString(validClient))
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
                .extract().body().jsonPath().getList("", ClientOutputDTO.class);

        return outputDTOList.get(outputDTOList.size() - 1).getUserId().toString();
    }


    private String getUUIDOfNewEquipment() throws JsonProcessingException {
        given()
                .header("Content-Type", "application/json")
                .body(obj.writeValueAsString(validEquipment))
                .when()
                .post(baseUrl + "equipment")
                .then()
                .statusCode(201);

        List<EquipmentOutputDTO> outputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("", EquipmentOutputDTO.class);

        return outputDTOList.get(outputDTOList.size() - 1).getEntityId().toString();
    }

    private String getUUIDOfCreatedRent(String rentString) throws JsonProcessingException {
        given()
                .header("Content-Type", "application/json")
                .body(rentString)
                .when()
                .post(baseUrl + "rents")
                .then()
                .log().all()
                .statusCode(201);

        List<RentOutputDTO> outputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "rents")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("", RentOutputDTO.class);

        return outputDTOList.get(outputDTOList.size() - 1).getRentId().toString();
    }
    /////////////////////////////////
}
