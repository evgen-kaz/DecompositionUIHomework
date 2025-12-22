package tests;

import api.AccountApiSteps;
import api.BookStoreApiSteps;
import extensions.WithLogin;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static io.qameta.allure.Allure.step;

@Owner("Evgenia Zhakova")
@Feature("Проверка функциональности API для управления товарами")
public class DemoqaTests extends TestBase {
    ProfilePage profilePage = new ProfilePage();
    AccountApiSteps accountApiSteps = new AccountApiSteps();
    BookStoreApiSteps bookStoreApiSteps = new BookStoreApiSteps();

    @Test
    @DisplayName("Удаление книги через UI")
    @Story("Удаление книги")
    @Tag("Positive")
    @Disabled("Проблемы на стороне сайта")
    void successfulDeleteBookUI() {
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
    @DisplayName("Удаление книги через API")
    @Story("Удаление книги")
    @Tag("Positive")
    @Disabled("Проблемы на стороне сайта")
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
    @DisplayName("Авторизация с помощью аннотации '@WithLogin' + удаление книги через API")
    @Story("Удаление книги")
    @Tag("Positive")
    void successfulAuthorizationAnnotationAndDeleteBookAPI() {
        step("Удаление всех книг через API", () -> {
            bookStoreApiSteps.AutWithAnnotationAndDeleteBooks();
        });

        step("Добавление книги через API", () -> {
            bookStoreApiSteps.AutWithAnnotationAndAddBook();
        });

        step("Удаление книги через API", () -> {
            bookStoreApiSteps.AutWithAnnotationAndDeleteBook();
        });
    }
}
