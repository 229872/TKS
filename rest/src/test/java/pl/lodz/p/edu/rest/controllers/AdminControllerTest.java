package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

class AdminControllerTest {

    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    AdminDTO validAdmin;
    String validAdminStr;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validAdmin = new AdminDTO(DataFaker.getAdmin());
        validAdminStr = obj.writeValueAsString(validAdmin);
    }

    // create

    @Test
    void createAdmin_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201)
                .body("login", equalTo(validAdmin.getLogin()));
    }

    @Test
    void createAdmin_noBody() {
        given()
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post("/admins")
                .then()
                .statusCode(400);
    }

    @Test
    void createAdmin_illegalValue() throws JsonProcessingException {
        validAdmin.setFavouriteIceCream("");
        String notValidAdminStr = obj.writeValueAsString(validAdmin);
        System.out.println(notValidAdminStr);
        given()
                .header("Content-Type", "application/json")
                .body(notValidAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(400);
    }

    @Test
    void createAdmin_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllAdmins_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneAdmin_byUUID_correct()  {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .extract().path("entityId");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", equalTo(uuid));
    }

    @Test
    void getOneAdmin_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneAdmin_byLogin_correct()  {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/login/" + login)
                .then()
                .statusCode(200)
                .body("login", equalTo(login));
    }

    @Test
    void getOneAdmin_byLogin_noSuchLogin()  {
        String login = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/login/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyAdminsByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFaker.randStr(30);
        validAdmin.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post("/admins")
                .then()
                .statusCode(201);

        validAdmin.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post("/admins")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    // update
    @Test
    void updateOneAdmin_correct() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String newFavouriteIceCream = "___other_favourite_ice_cream___";
        validAdmin.setFavouriteIceCream(newFavouriteIceCream);
        String updatedAdminStr = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(updatedAdminStr)
                .when()
                .put("/admins/" + uuid)
                .then()
                .statusCode(200);
        String favouriteIceCream = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/" + uuid)
                .then()
                .statusCode(200)
                .extract().path("favouriteIceCream");
        assertEquals(newFavouriteIceCream, favouriteIceCream);
    }

    @Test
    void updateOneAdmin_noAdmin() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .put("/admins/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneAdmin_updateLogin() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validAdmin.setLogin("__other_login__");
        String updatedLogin = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/admins/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void updateOneAdmin_illegalValues() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validAdmin.setFavouriteIceCream("");
        String updatedLogin = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/admins/" + uuid)
                .then()
                .statusCode(400);
    }

    @Test
    void activateDeactivateTest() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post("/admins")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        // deactivate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/admins/" + uuid + "/deactivate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/" + uuid)
                .then()
                .body("active", equalTo(false));

        // activate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put("/admins/" + uuid + "/activate")
                .then()
                .statusCode(204);
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/admins/" + uuid)
                .then()
                .body("active", equalTo(true));
    }
}