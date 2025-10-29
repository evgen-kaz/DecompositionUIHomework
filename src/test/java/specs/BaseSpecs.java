package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import tests.TestBase;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class BaseSpecs extends TestBase {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .header("Accept", "application/json")
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static ResponseSpecification responseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(HEADERS)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static RequestSpecification getAuthRequestSpec(String token) {
        return with()
                .filter(withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .log().uri()
                .log().body()
                .log().headers()
                .contentType(JSON);
    }
}