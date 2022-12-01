package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.lodz.p.edu.data.model.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

class EmployeeControllerTest {

    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    EmployeeDTO validEmployee;
    String validEmployeeStr;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validEmployee = new EmployeeDTO(DataFaker.getEmployee());
        validEmployeeStr = obj.writeValueAsString(validEmployee);
    }

    // create

    @Test
    void createEmployee_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
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
                .post("/employees")
                .then()
                .statusCode(400);
    }

    @Test
    void createEmployee_illegalValue() throws JsonProcessingException {
        validEmployee.setDesk("");
        String notValidEmployeeStr = obj.writeValueAsString(validEmployee);
        System.out.println(notValidEmployeeStr);
        given()
                .header("Content-Type", "application/json")
                .body(notValidEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(400);
    }

    @Test
    void createEmployee_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllEmployees_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneEmployee_byUUID_correct()  {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .extract().path("entityId");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", equalTo(uuid));
    }

    @Test
    void getOneEmployee_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneEmployee_byLogin_correct()  {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/login/" + login)
                .then()
                .statusCode(200)
                .body("login", equalTo(login));
    }

    @Test
    void getOneEmployee_byLogin_noSuchLogin()  {
        String login = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/login/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyEmployeesByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFaker.randStr(30);
        validEmployee.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post("/employees")
                .then()
                .statusCode(201);

        validEmployee.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post("/employees")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    // update
    @Test
    void updateOneEmployee_correct() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String newDesk = "___other_desk___";
        validEmployee.setDesk(newDesk);
        String updatedEmployeeStr = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(updatedEmployeeStr)
                .when()
                .put("/employees/" + uuid)
                .then()
                .statusCode(200);
        String desk = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/" + uuid)
                .then()
                .statusCode(200)
                .extract().path("desk");
        assertEquals(newDesk, desk);
    }

    @Test
    void updateOneEmployee_noEmployee() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .put("/employees/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneEmployee_updateLogin() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validEmployee.setLogin("__other_login__");
        String updatedLogin = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/employees/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateOneEmployee_illegalValues() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validEmployee.setDesk("");
        String updatedLogin = obj.writeValueAsString(validEmployee);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/employees/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void activateDeactivateTest() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEmployeeStr)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        // deactivate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/employees/" + uuid + "/deactivate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/" + uuid)
                .then()
                .body("active", equalTo(false));

        // activate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/employees/" + uuid + "/activate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/employees/" + uuid)
                .then()
                .body("active", equalTo(true));
    }
}