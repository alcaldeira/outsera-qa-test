package br.com.aval.testes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    String baseUrl = "https://jsonplaceholder.typicode.com";

    @Test
    public void validarGetPostsComSucesso() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test
    public void validarGetPostInexistente() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/posts/9999")
            .then()
                .statusCode(404);
    }

    @Test
    public void verificarGetCommentsComSucesso() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/comments")
            .then()
                .statusCode(200);
    }

    @Test
    public void verificarGetPostsComSucesso() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0));
    }

    @Test
    public void validarSchemaDoPost() {
        RestAssured
            .given()
                .baseUri(baseUrl)
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }
}