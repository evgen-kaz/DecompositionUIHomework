package tests;

import api.AccountApiSteps;
import api.BookStoreApiSteps;
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
    AccountApiSteps accountApiSteps = new AccountApiSteps();
    BookStoreApiSteps bookStoreApiSteps = new BookStoreApiSteps();

    @Test
    @Tag("Positive")
    @DisplayName("Удаление книги через UI")
    void successfulDeleteBookUI(){
        step("Авторизация пользователя через API", () -> {
            accountApiSteps.login();
        });
        step("Удаление всех книг через API", () -> {
            bookStoreApiSteps.deleteBooks(accountApiSteps.tokenGet, accountApiSteps.userIDGet);
        });
        step("Добавление книги через API", () -> {
            bookStoreApiSteps.addBook(accountApiSteps.tokenGet);
        });
        step("Добавление куки и открытие страницы с профилем", () -> {
            profilePage.openProfilePageAndSetCookie(accountApiSteps.tokenGet, accountApiSteps.expiresGet, accountApiSteps.userIDGet);
        });
        step("Проверка пустого списка после удаления книги в UI", () -> {
            profilePage.checkDeleteBook();
        });
    }

    @Test
    @Tag("Positive")
    @DisplayName("Удаление книги через API")
    void successfulDeleteBookAPI() {
        step("Авторизация пользователя через API", () -> {
            accountApiSteps.login();
        });

        step("Удаление всех книг через API", () -> {
            bookStoreApiSteps.deleteBooks(accountApiSteps.tokenGet, accountApiSteps.userIDGet);
        });

        step("Добавление книги через API", () -> {
            bookStoreApiSteps.addBook(accountApiSteps.tokenGet);
        });

        step("Удаление книги через API", () -> {
                bookStoreApiSteps.deleteBook(accountApiSteps.tokenGet, accountApiSteps.userIDGet);
        });

        step("Добавление куки и открытие страницы с профилем", () -> {
        profilePage.openProfilePageAndSetCookie(accountApiSteps.tokenGet, accountApiSteps.expiresGet, accountApiSteps.userIDGet);
        });

        step("Проверка пустого списка после удаления книги в UI", () -> {
        profilePage.checkDeleteBook();
        });
    }

    @Test
    @WithLogin
    @Tag("Positive")
    @DisplayName("Авторизация с помощью аннотации '@WithLogin' + удаление книги через API")
    void successfulAuthorizationAnnotationAndDeleteBookAPI() {
        step("Удаление всех книг через API", () -> {
            bookStoreApiSteps.AutWithAnnotationAndDeleteBooks();
        });

        step("Добавление книги через API", () -> {
            bookStoreApiSteps.AutWithAnnotationAndAddBook();
        });

        step("Удаление книги через API", () ->{
            bookStoreApiSteps.AutWithAnnotationAndDeleteBook();
        });
    }
}