
package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.*;

public class RegresApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test(description = "ListUsers")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("list user")
    @Story("List all user")
    public void testListUsers() {
        given()
            .queryParam("page", 2)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("data", not(empty()));
    }

    @Test(description = "ListSingleUser")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("list user")
    @Story("test single user")
    public void testSingleUser() {
        when()
            .get("/api/users/2")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(2));
    }

//    @Test(description = "testSingleUserNotFound")
//    @Severity(SeverityLevel.CRITICAL)
//    @Feature("list user")
//    @Story("test single user")
//    public void testSingleUserNotFound() {
//        when()
//            .get("/api/users/23")
//        .then()
//            .statusCode(404);
//    }

//    @Test(description = "createUser")
//    @Severity(SeverityLevel.CRITICAL)
//    @Feature("create user")
//    @Story("create user")
//    public void testCreateUser() {
//        //String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
//
//        Map<String, String> payload = new HashMap<>();
//        payload.put("name", "morpheus");
//        payload.put("job", "leader");
//
//        given()
//            .contentType(ContentType.JSON)
//            .body(payload)
//        .when()
//            .post("/api/users")
//        .then()
//            .statusCode(201)
//            .body("name", equalTo("morpheus"));
//    }


//    @Test(description = "testUpdateUserPut")
//    @Severity(SeverityLevel.CRITICAL)
//    @Feature("update user")
//    @Story("update user")
//    public void testUpdateUserPut() {
//        //String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
//
//        Map<String, String> payload = new HashMap<>();
//        payload.put("name", "morpheus");
//        payload.put("job", "leader");
//
//        given()
//            .contentType(ContentType.JSON)
//            .header("x-api-key", "reqres-free-v1")
//            .body(payload)
//        .when()
//            .put("/api/users/2")
//        .then()
//            .statusCode(200)
//            .body("job", equalTo("zion resident"));
//    }

    @Test(description = "testUpdateUserPatch")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("update user")
    @Story("update user")
    public void testUpdateUserPatch() {
        //String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        Map<String, String> payload = new HashMap<>();
        payload.put("name", "morpheus");
        payload.put("job", "leader");

        given()
            .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
            .body(payload)
        .when()
            .patch("/api/users/2")
        .then()
            .statusCode(200)
            .body("job", equalTo("zion leader"));
    }
//
//    @Test(description = "testDeleteUser")
//    @Severity(SeverityLevel.CRITICAL)
//    @Feature("delete user")
//    @Story("delete user")
//    public void testDeleteUser() {
//        when()
//            .delete("/api/users/2")
//        .then()
//            .statusCode(204);
//    }

//    @Test(description = "testRegisterSuccessful")
//    @Severity(SeverityLevel.CRITICAL)
//    @Feature("successful login")
//    @Story("successful login")
//    public void testRegisterSuccessful() {
//        //String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
//
//        Map<String, String> payload = new HashMap<>();
//        payload.put("name", "morpheus");
//        payload.put("job", "leader");
//
//        given()
//            .contentType(ContentType.JSON)
//            .header("x-api-key", "reqres-free-v1")
//            .body(payload)
//        .when()
//            .post("/api/register")
//        .then()
//            .statusCode(200)
//            .body("id", notNullValue())
//            .body("token", notNullValue());
//    }

    @Test(description = "testRegisterUnSuccessful")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("unsuccessful login")
    @Story("unsuccessful login")
    public void testRegisterUnsuccessful() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "sydney@fife");


        given()
            .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
            .body(payload)
        .when()
            .post("/api/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    @Test(description = "testLoginSuccessful")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("successful login")
    @Story("successful login")
    public void testLoginSuccessful() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "eve.holt@reqres.in");
        payload.put("password", "cityslicka");

        given()
            .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
            .body(payload)
        .when()
            .post("/api/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue());
    }

    @Test(description = "testLoginUnsuccessful")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("unsuccessful login")
    @Story("unsuccessful login")
    public void testLoginUnsuccessful() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "peter@klaven");

        given()
            .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1")
            .body(payload)
        .when()
            .post("/api/login")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    @Test(description = "testDelayedResponse")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("delayed response")
    @Story("delayed response")
    public void testDelayedResponse() {
        given()
                .header("x-api-key", "reqres-free-v1")
            .queryParam("delay", 3)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("data", not(empty()));
    }
}
