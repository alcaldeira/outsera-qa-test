import Utils.Environment;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected RequestSpecification request(Environment environment) {
        return given().baseUri(environment.getBaseUrl());
    }
}
