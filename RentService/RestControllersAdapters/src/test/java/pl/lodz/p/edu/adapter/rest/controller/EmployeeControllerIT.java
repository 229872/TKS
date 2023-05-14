package pl.lodz.p.edu.adapter.rest.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.edu.adapter.rest.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.EmployeeOutputDTO;

import java.util.List;
import java.util.UUID;

@Testcontainers
public class EmployeeControllerIT extends AppDeploymentTestConfig {
    ObjectMapper obj = new ObjectMapper();
    EmployeeInputDTO validEmployee;
    String validEmployeeStr;

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validEmployee = DataFakerRestControllerInputDTO.getEmployee();
        validEmployeeStr = obj.writeValueAsString(validEmployee);
    }

    // create

    @Test
    void createEmployee_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(201)
                .body("login", equalTo(validEmployee.getLogin()));
    }

    @Test
    void createEmployee_noBody() {
        given()
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(400);
    }

    @Test
    void createEmployee_illegalValue() throws JsonProcessingException {
        EmployeeInputDTO newValidClient = DataFakerRestControllerInputDTO.getEmployee();
        newValidClient.setDesk("");
        String notValidEmployeeStr = obj.writeValueAsString(newValidClient);

        given()
                .header("Content-Type", "application/json")
                .body(notValidEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(417);
    }

    @Test
    void createEmployee_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllEmployees_correct() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneEmployee_byUUID_correct() {
        String uuid = getUUIDOfNewObject();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(200);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(200)
                .body("userId", IsEqual.equalTo(uuid));
    }


    @Test
    void getOneEmployee_byUUID_noSuchUUID() {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneEmployee_byLogin_correct() {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/login/" + login)
                .then()
                .statusCode(200)
                .body("login", equalTo(login));
    }

    @Test
    void getOneEmployee_byLogin_noSuchLogin() {
        String login = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/login/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyEmployeesByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFakerRestControllerInputDTO.randStr(30);
        validEmployee.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validEmployee);
        List<EmployeeOutputDTO> initialOutputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("", EmployeeOutputDTO.class);
        int initialSize = initialOutputDTOList.size();

        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(201);

        validEmployee.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(initialSize + 2));
    }

    // update

    @Test
    void updateOneEmployee_noEmployee() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .put(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneEmployee_updateLogin() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();
        String oldLogin = validEmployee.getLogin();

        String newLogin = "__other_login__";
        validEmployee.setLogin(newLogin);

        String updatedLogin = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(200);

        Assertions.assertNotEquals(oldLogin, given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/" + uuid)
                .then()
                .extract().path("login"));

        Assertions.assertEquals(newLogin, given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "employees/" + uuid)
                .then()
                .extract().path("login"));
    }

    @Test
    void updateOneEmployee_illegalValues() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();

        validEmployee.setDesk("");
        String updatedLogin = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "employees/" + uuid)
                .then()
                .statusCode(417);
    }

    //fixme doesn't work
//    @Test
//    void activateDeactivateTest() throws JsonProcessingException {
//        String uuid = UUID.randomUUID().toString();
//
//        // deactivate
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .put(baseUrl + "employees/" + uuid + "/deactivate")
//                .then()
//                .statusCode(204);
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(baseUrl + "employees/" + uuid)
//                .then()
//                .body("active", equalTo(false));
//
//        // activate
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .put(baseUrl + "employees/" + uuid + "/activate")
//                .then()
//                .statusCode(204);
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(baseUrl + "employees/" + uuid)
//                .then()
//                .body("active", equalTo(true));
//    }


    private String getUUIDOfNewObject() {
        return String.valueOf(given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post(baseUrl + "employees")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getUUID("userId"));
    }
}
