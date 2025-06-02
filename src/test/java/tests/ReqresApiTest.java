package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReqresApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetUsers() {
        Response response = given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "User list is empty");
    }

    @Test
    public void testCreateUser() {
        String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
    }
}
