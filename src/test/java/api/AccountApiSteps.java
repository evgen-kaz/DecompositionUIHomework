package api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.AuthorizationResponseModel;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.BaseSpecs.*;
import static utils.DataTest.AUTH_DATA;

import static io.restassured.RestAssured.given;
import static utils.DataTest.AUTH_DATA_WITH_EXPIRED_TOKEN;

public class AccountApiSteps {
    public String tokenGet;
    public String userIDGet;
    public String expiresGet;

    @DisplayName("Авторизация под пользователем корректными данными")
    public AuthorizationResponseModel login() {
        RestAssured.defaultParser = Parser.JSON;
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

    @DisplayName("Авторизация под пользователем с просроченным токеном")
    public AuthorizationResponseModel loginUserWithExpiredToken() {
        RestAssured.defaultParser = Parser.JSON;
        AuthorizationResponseModel responseAuthUser =
                given(requestSpec)
                        .body(AUTH_DATA_WITH_EXPIRED_TOKEN)
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

    @DisplayName("Неуспешное удаление пользователя по несуществущему UUID")
    public void deleteNonExistentUser(String tokenGet) {
        UUID uuid = UUID.randomUUID();
        given(getAuthRequestSpec(tokenGet))
                .when()
                .delete("/Account/v1/User?UserId=" + uuid)
                .then()
                .spec(responseSpec(404));
    }


}
