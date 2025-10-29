package extensions;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.AuthorizationResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpec;

import static utils.DataTest.AUTH_DATA;

public class LoginExtensions implements BeforeEachCallback {
    private static final String USER_ID = "userID";
    private static final String EXPIRES = "expires";
    private static final String TOKEN = "token";
    public static String token;
    public static String userID;

    @Override
    @Step("Авторизоваться в Book Store")
    public void beforeEach(ExtensionContext context) {
        RestAssured.defaultParser = Parser.JSON;

        AuthorizationResponseModel authorizationResponseModel = given(requestSpec)
                .body(AUTH_DATA)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(200))
                .extract().as(AuthorizationResponseModel.class);

        open("https://demoqa.com/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie(USER_ID, authorizationResponseModel.getUserId())); //установили userID
        getWebDriver().manage().addCookie(new Cookie(EXPIRES, authorizationResponseModel.getExpires())); //установили когда токен истекает
        getWebDriver().manage().addCookie(new Cookie(TOKEN, authorizationResponseModel.getToken())); //установили токен

        token = authorizationResponseModel.getToken();
        userID = authorizationResponseModel.getUserId();
    }
}