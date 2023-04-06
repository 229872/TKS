package pl.lodz.p.edu.adapter.rest.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class AdminControllerIT extends AppDeploymentTestConfig {

    ObjectMapper obj = new ObjectMapper();
    AdminInputDTO validAdmin;
    String validAdminStr;

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validAdmin = DataFakerRestControllerInputDTO.getAdmin();
        validAdminStr = obj.writeValueAsString(validAdmin);
    }

    // create

    @Test
    void createAdmin_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post(baseUrl + "admins")
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
                .post(baseUrl + "admins")
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
                .post(baseUrl + "admins")
                .then()
                .statusCode(417);
    }

    @Test
    void createAdmin_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post(baseUrl + "admins")
                .then()
                .statusCode(201);

        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post(baseUrl + "admins")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllAdmins_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneAdmin_byUUID_correct()  {
        String uuid = getUUIDOfNewObject();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(200);


        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(200)
                .body("userId", equalTo(uuid));
    }

    @Test
    void getOneAdmin_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void getOneAdmin_byLogin_correct()  {
        String login = given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post(baseUrl + "admins")
                .then()
                .extract().path("login");

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/login/" +  login)
                .then()
                .statusCode(200)
                .body("login", equalTo(login));
    }

    @Test
    void getOneAdmin_byLogin_noSuchLogin()  {
        String login = "noSuchLogin";
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + login)
                .then()
                .statusCode(404);
    }

    @Test
    void getManyAdminsByLogin_correct() throws JsonProcessingException {
        String uniqueLogin = DataFakerRestControllerInputDTO.randStr(30);
        validAdmin.setLogin(uniqueLogin);
        String str1 = obj.writeValueAsString(validAdmin);
        List<AdminOutputDTO> initialOutputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("", AdminOutputDTO.class);
        int initialSize = initialOutputDTOList.size();

        given()
                .header("Content-Type", "application/json")
                .body(str1)
                .when()
                .post(baseUrl + "admins")
                .then()
                .statusCode(201);

        validAdmin.setLogin(uniqueLogin + "1");
        String str2 = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(str2)
                .when()
                .post(baseUrl + "admins")
                .then()
                .statusCode(201);

        // test
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins?login=" + uniqueLogin)
                .then()
                .statusCode(200)
                .body("size()", is(initialSize + 2));
    }

    // update
    @Test
    void updateOneAdmin_correct() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();
        System.out.println(uuid);

        String newFavouriteIceCream = "___other_favourite_ice_cream___";
        validAdmin.setFavouriteIceCream(newFavouriteIceCream);
        String updatedAdminStr = obj.writeValueAsString(validAdmin);
        System.out.println(updatedAdminStr);
        given()
                .header("Content-Type", "application/json")
                .body(updatedAdminStr)
                .when()
                .put(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(200)
                .log().all();

        String favouriteIceCream = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(200).log().all()
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
                .put(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneAdmin_updateLogin() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();

        validAdmin.setLogin("__other_login__");
        String updatedLogin = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "admins/" + uuid);


    }

    @Test
    void updateOneAdmin_illegalValues() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();
        String oldLogin = validAdmin.getLogin();
        validAdmin.setFavouriteIceCream("");
        String updatedLogin = obj.writeValueAsString(validAdmin);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put(baseUrl + "admins/" + uuid)
                .then()
                .statusCode(417);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .body("login", equalTo(oldLogin));
    }

    @Test
    void activateDeactivateTest() throws JsonProcessingException {
        String uuid = getUUIDOfNewObject();

        // deactivate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put(baseUrl + "admins/" + uuid + "/deactivate")
                .then()
                .statusCode(204);

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .body("active", equalTo(false));

        // activate
        given()
                .header("Content-Type", "application/json")
                .when()
                .put(baseUrl + "admins/" + uuid + "/activate")
                .then()
                .statusCode(204)
                .log().all();

        given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins/" + uuid)
                .then()
                .body("active", equalTo(true));
    }

    private String getUUIDOfNewObject() {
        given()
                .header("Content-Type", "application/json")
                .body(validAdminStr)
                .when()
                .post(baseUrl + "admins")
                .then()
                .statusCode(201);

        List<AdminOutputDTO> outputDTOList = given()
                .header("Content-Type", "application/json")
                .when()
                .get(baseUrl + "admins")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("", AdminOutputDTO.class);

        return outputDTOList.get(0).getUserId().toString();
    }
}
