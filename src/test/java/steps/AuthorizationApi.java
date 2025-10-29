/*
package steps;
import models.AuthorizationRequestModel;
import models.AuthorizationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.requestSpec;
import static specs.BaseSpecs.responseSpec;

public class AuthorizationApi {

    private static final String USER_ID = "userID";
    private static final String EXPIRES = "expires";
    private static final String TOKEN = "token";

    @DisplayName("Для входа по имени и паролю")
    public AuthorizationRequestModel login (AuthorizationRequestModel user) {
        return given(requestSpec)
                .body(user)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec(200))
                .extract().as(AuthorizationRequestModel.class);
    }

    @DisplayName("Настройка авторизационной куки")
    public void setAuthCookies (AuthorizationResponseModel user) {
        open("https://demoqa.com/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie(USER_ID, user.getUserId())); //установили userID
        getWebDriver().manage().addCookie(new Cookie(EXPIRES, user.getExpires())); //установили когда токен истекает
        getWebDriver().manage().addCookie(new Cookie(TOKEN, user.getToken())); //установили токен
        //open("/profile");
    }


}
*/
