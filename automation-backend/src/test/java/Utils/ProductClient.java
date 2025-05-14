package Utils;

import Request.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductClient {

    private static final String BASE_URL = Environment.dummy.getBaseUrl();

    public static Response getAllProducts() {
        return RestAssured
                .given()
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/products");
    }

    public static int getFirstProductId() {
        return getAllProducts()
                .then()
                .statusCode(200)
                .extract()
                .path("products[0].id");
    }

    public static Response getProductById(int id) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/products/" + id);
    }

    public static Response deleteProductById(int id) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/products/" + id);
    }

    public static Response createProduct(ProductRequest product) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .post("/products/add");
    }

    public static Response updateProductById(int id, ProductRequest product) {
        return RestAssured
                .given()
                .baseUri(Environment.dummy.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .put("/products/" + id);
    }
}