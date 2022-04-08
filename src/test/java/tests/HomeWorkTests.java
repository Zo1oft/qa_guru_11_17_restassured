package tests;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class HomeWorkTests {

    @Test
    void SingleUserNotFound() {
        String response =
                get("https://reqres.in/api/users/23")
                        .then()
                        .statusCode(404)
                        .extract().response().asString();

        System.out.println(response);
    }

    @Test
    void PostCreate() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
                given()
                        .contentType(JSON)
                        .body(data)
                        .when()
                        .post("https://reqres.in/api/users")
                        .then()
                        .statusCode(201)
                        .body("name", is("morpheus"))
                        .body("job", is("leader"));
    }

    @Test
    void LoginSuccessful() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void ListUsers() {
        String response =
                get("https://reqres.in/api/users?page=2")
                        .then()
                        .statusCode(200)
                        .extract().response().asString();
        System.out.println(response);

    }

    @Test
    void DeteteTest() {
        String response =
        delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .extract().response().asString();
        System.out.println(response);
    }

    @Test
    void DelayedTest() {
        String response =
        get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .extract().response().asString();
        System.out.println(response);
    }

}
