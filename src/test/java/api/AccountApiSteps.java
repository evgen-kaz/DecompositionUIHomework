package api;

import models.AuthorizationResponseModel;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.DataTest.AUTH_DATA;

import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpec;

public class AccountApiSteps {
    public String tokenGet;
    public String userIDGet;
    public String expiresGet;

    public AuthorizationResponseModel login() {
        AuthorizationResponseModel responseAuthUser =
                given(requestSpec)
                        .body(AUTH_DATA)
                        .when()
                        .post("/Account/v1/Login")

                        .then()
                        .spec(responseSpec(200))
                        .extract().as(AuthorizationResponseModel.class);
        step("Проверка наличия токена в ответе", () -> {
            assertNotNull(responseAuthUser.getToken());
            assertNotNull(responseAuthUser.getUserId());
            assertNotNull(responseAuthUser.getExpires());
        });
        tokenGet = responseAuthUser.getToken();
        userIDGet = responseAuthUser.getUserId();
        expiresGet = responseAuthUser.getExpires();
        return responseAuthUser;
    }
}
