package Utils;

import Request.LoginRequest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthHooks {

    private static String token;

    public static String getAuthToken(String username, String password) {
        if (token == null) {
            token =
                given()
                    .baseUri(Environment.dummy.getBaseUrl())
                    .contentType(ContentType.JSON)
                    .body(new LoginRequest(username, password))
                .when()
                    .post("/auth/login")
                .then()
                    .statusCode(200)
                    .extract()
                    .path("accessToken");
        }
        return token;
    }

    public static void clearToken() {
        token = null;
    }
}