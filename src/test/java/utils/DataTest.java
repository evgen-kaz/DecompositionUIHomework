package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import io.restassured.response.Response;
import models.AuthorizationRequestModel;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DataTest {
    public static final String USER_NAME = System.getProperty("userName", "Anna@Test");
    public static final String PASSWORD = System.getProperty("password", "12345!ZzAa");
    public static final String ISBN = System.getProperty("isbn", "9781449325862");

    public static final AuthorizationRequestModel AUTH_DATA = new AuthorizationRequestModel(PASSWORD, USER_NAME);
}
