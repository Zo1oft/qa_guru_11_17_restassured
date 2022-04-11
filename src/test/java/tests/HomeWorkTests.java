package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;

public class HomeWorkTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void singleUserNotFound() {
        String response =
                get("/api/users/23")
                        .then()
                        .statusCode(404)
                        .extract().response().asString();

        System.out.println(response);
    }

    @Test
    void postCreate() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void loginSuccessful() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    void listUsers() {
        get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data", is(notNullValue()));

    }

    @Test
    void deleteTest() {
        String response =
                delete("/api/users/2")
                        .then()
                        .statusCode(204)
                        .extract().response().asString();
        System.out.println(response);
    }

    @Test
    void delayedTest() {
        String response =
                get("/users?delay=3")
                        .then()
                        .statusCode(200)
                        .extract().response().asString();
        System.out.println(response);
    }
}