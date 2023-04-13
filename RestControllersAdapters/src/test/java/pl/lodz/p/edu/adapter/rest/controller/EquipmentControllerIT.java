package pl.lodz.p.edu.adapter.rest.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class EquipmentControllerIT extends AppDeploymentTestConfig {

    ObjectMapper obj = new ObjectMapper();
    EquipmentInputDTO validEquipment;
    String validEquipmentStr;

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validEquipment = DataFakerRestControllerInputDTO.getEquipment();
        validEquipmentStr = obj.writeValueAsString(validEquipment);
    }

    // create

    @Test
    void createEquipment_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post(baseUrl + "equipment")
                .then()
                .statusCode(201);
    }

    @Test
    void createEquipment_noBody() {
        given()
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post(baseUrl + "equipment")
                .then()
                .statusCode(400);
    }

    @Test
    void createEquipment_illegalValue() throws JsonProcessingException {
        validEquipment.setFirstDayCost(-1000);
        String notValidEquipmentStr = obj.writeValueAsString(validEquipment);
        System.out.println(notValidEquipmentStr);
        given()
                .header("Content-Type", "application/json")
                .body(notValidEquipmentStr)
                .when()
                .post(baseUrl + "equipment")
                .then()
                .statusCode(417); //EXPECTATION FAILED @VALID
    }

    // read
    @Test
    void getAllEquipments_correct() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneEquipment_byUUID_correct() {
        String uuid = getUUIDOfNewObject();
        System.out.println(uuid);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", Matchers.equalTo(uuid));
    }

    @Test
    void getOneEquipment_byUUID_noSuchUUID() {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(404);
    }

    // update
    @Test
    void updateOneEquipment_correct() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(200);

        String newName = "___other_name___";
        validEquipment.setName(newName);
        String updatedEq = obj.writeValueAsString(validEquipment);

        given()
                .header("Content-Type", "application/json")
                .body(updatedEq)
                .when()
                .put(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(200);

        String name = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(200)
                .extract().path("name");
        assertEquals(newName, name);
    }

    @Test
    void updateOneEquipment_noEquipment() {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .put(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneEquipment_illegalValues() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();

        validEquipment.setName(null);
        String updatedLogin = obj.writeValueAsString(validEquipment);

        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(417);
    }

    @Test
    void deleteOneEquipment_correct() {

        String uuid = getUUIDOfNewObject();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(200);
        // delete
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(204);
        // get
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(404);
    }

//TODO
//    @Test
//    void deleteOneEquipment_withExistingRent() throws JsonProcessingException {
//        String equipmentId = given()
//                .header("Content-Type", "application/json")
//                .body(validEquipmentStr)
//                .when()
//                .post(baseUrl + "/equipment")
//                .then()
//                .statusCode(201)
//                .extract().path("entityId");
//
//        ClientInputDTO client = DataFakerRestControllerInputDTO.getClient();
//        String clientStr = obj.writeValueAsString(client);
//        String clientId = given()
//                .header("Content-Type", "application/json")
//                .body(clientStr)
//                .when()
//                .post(baseUrl + "/clients")
//                .then()
//                .statusCode(201)
//                .extract().path("entityId");
//
//        RentInputDTO rent = new RentInputDTO(clientId, equipmentId, "2023-04-05T12:38:35.585", null);
//        String rentStr = obj.writeValueAsString(rent);
//
//        System.out.println(rentStr);
//
//        given()
//                .header("Content-Type", "application/json")
//                .body(rentStr)
//                .when()
//                .post(baseUrl + "/rents")
//                .then()
//                .statusCode(201);
//
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .delete(baseUrl + "/equipment/" + equipmentId)
//                .then()
//                .statusCode(409);
//    }

    @Test
    void deleteOneEquipment_notExist() {
        String uuid = UUID.randomUUID().toString();
        // delete
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(baseUrl + "equipment/" + uuid)
                .then()
                .statusCode(204);
    }

    private String getUUIDOfNewObject() {
        return String.valueOf(given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post(baseUrl + "equipment")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getUUID("entityId"));
    }
}
