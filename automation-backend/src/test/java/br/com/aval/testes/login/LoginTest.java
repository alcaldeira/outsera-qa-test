package br.com.aval.testes.login;

import Request.LoginRequest;
import Utils.Environment;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    @Test
    public void loginSuccess() {
        LoginRequest payload = new LoginRequest("emilys", "emilyspass");

        given()
            .baseUri(Environment.dummy.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
            .log().all()
        .when()
            .post("/auth/login")
        .then()
            .log().all()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/login-schema.json"));
    }

    @Test
    public void verifyLoginFailure() {
        LoginRequest payload = new LoginRequest("emilys", "3232");

        given()
            .baseUri(Environment.dummy.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
            .log().all()
        .when()
            .post("/auth/login")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", equalTo("Invalid credentials"));
    }

    @Test
    public void verifyLoginEmptyUsernameAndPassword() {
        LoginRequest payload = new LoginRequest("", "");

        given()
            .baseUri(Environment.dummy.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
            .log().all()
        .when()
            .post("/auth/login")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", equalTo("Username and password required"));
    }

}