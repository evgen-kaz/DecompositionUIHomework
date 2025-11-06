package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import extensions.LoginExtensions;
import extensions.WithLogin;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.EndPoint.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.BaseSpecs.*;
import static utils.DataTest.*;

public class DemoqaTests extends TestBase {
    ProfilePage profilePage = new ProfilePage();

    @Test
    @Tag("Positive")
    @DisplayName("Удаление книги через UI")
    void successfulDeleteBookUI() throws JsonProcessingException {
        Map<String, String> passwordAndUserName = new HashMap<>();
        passwordAndUserName.put("password", PASSWORD);
        passwordAndUserName.put("userName", USER_NAME);

        AuthorizationResponseModel responseAuthUser = step("Авторизация пользователя через API", () ->
                given(requestSpec)
                        .body(passwordAndUserName)
                        .when()
                        .post(LOGIN_END_POINT)
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(AuthorizationResponseModel.class));
        step("Проверка наличия токена в ответе", () -> {
            assertNotNull(responseAuthUser.getToken());
        });

        RestAssured.defaultParser = Parser.JSON;
        step("Удаление через API всех книг", () ->
                given(getAuthRequestSpec(responseAuthUser.getToken()))
                        .when()
                        .delete(BOOKS_WITH_USER_ID + responseAuthUser.getUserId())
                        .then()
                        .spec(responseSpec(204)));

        ObjectMapper mapper = new ObjectMapper();
        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));
        String jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel responseAddBookModel = step("Добавление книги через API", () ->
                given(getAuthRequestSpec(responseAuthUser.getToken()))
                        .body(jsonBody)
                        .when()
                        .post(BOOKS_END_POINT)
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class));
        step("Проверка добавления книги Ччерез API", () -> {
            assertEquals(List.of(isbnModel), responseAddBookModel.getBooks());
        });

        profilePage.openProfilePageAndSetCookie(responseAuthUser.getUserId(), responseAuthUser.getExpires(), responseAuthUser.getToken());
        profilePage.checkDeleteBook();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Удаление книги через API")
    void successfulDeleteBookAPI() throws JsonProcessingException {
        Map<String, String> passwordAndUserName = new HashMap<>();
        passwordAndUserName.put("password", PASSWORD);
        passwordAndUserName.put("userName", USER_NAME);

        AuthorizationResponseModel responseAuthUser = step("Авторизация пользователя через API", () ->
                given(requestSpec)
                        .body(passwordAndUserName)
                        .when()
                        .post(LOGIN_END_POINT)
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(AuthorizationResponseModel.class));
        step("Проверка, что пришел токен", () -> {
            assertNotNull(responseAuthUser.getToken());
        });

        RestAssured.defaultParser = Parser.JSON;
        step("Удаление книг через API", () ->
                given(getAuthRequestSpec(responseAuthUser.getToken()))
                        .when()
                        .delete(BOOKS_WITH_USER_ID + responseAuthUser.getUserId())
                        .then()
                        .spec(responseSpec(204)));


        ObjectMapper mapper = new ObjectMapper();

        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));

        String jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel responseAddBookModel = step("Добавление книги через API", () ->
                given(getAuthRequestSpec(responseAuthUser.getToken()))
                        .body(jsonBody)
                        .when()
                        .post(BOOKS_END_POINT)
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class));
        step("Проверка данных ответа для списка книг", () -> {
            assertEquals(List.of(isbnModel), responseAddBookModel.getBooks());
        });

        step("Удаление книги с ISBN {isbn} через API", () ->
                given(getAuthRequestSpec(responseAuthUser.getToken()))
                        .body(jsonBody)
                        .when()
                        .delete(BOOKS_WITH_USER_ID + responseAuthUser.getUserId())
                        .then()
                        .spec(responseSpec(204)));

        profilePage.openProfilePageAndSetCookie(responseAuthUser.getUserId(), responseAuthUser.getExpires(), responseAuthUser.getToken());
        profilePage.checkDeleteBook();
    }

    @Test
    @WithLogin
    @Tag("Positive")
    @DisplayName("Авторизация с помощью аннотации '@WithLogin' + удаление книги через API")
    void successfulAuthorizationAnnotationAndDeleteBookAPI() throws JsonProcessingException {
        String token = LoginExtensions.token;
        String userId = LoginExtensions.userID;

        RestAssured.defaultParser = Parser.JSON;
        step("Удаление книг через API", () ->
                given(getAuthRequestSpec(token))
                        .when()
                        .delete(BOOKS_WITH_USER_ID + userId)
                        .then()
                        .spec(responseSpec(204)));

        ObjectMapper mapper = new ObjectMapper();

        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));

        String jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel response = step("Добавление книги через API", () ->
                given(getAuthRequestSpec(token))
                        .body(jsonBody)
                        .when()
                        .post(BOOKS_END_POINT)
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class));
        step("Проверка ответа на добавление книги", () -> {
            assertEquals(List.of(isbnModel), response.getBooks());
        });

        step("Удаление книги с ISBN {isbn} через API", () ->
                given(getAuthRequestSpec(token))
                        .body(jsonBody)
                        .when()
                        .delete(BOOKS_WITH_USER_ID + userId)
                        .then()
                        .spec(responseSpec(204)));
    }
}
