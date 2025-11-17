package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {
    SelenideElement
            userNameLocator = $("#userName-value"),
            buttonDeleteBooksLocator = $(withTagAndText("button", "Delete All Books")),
            windowConfirmationDeleteLocator = $(".modal-content").$("#closeSmallModal-ok"),
            notBeButtonDeleteBookLocator = $("#delete-record-undefined");

    String expectedUserName = "Anna@Test";

    @DisplayName("Добавление куки и открытие страницы с профилем")
    public void openProfilePageAndSetCookie(String tokenGet, String userIDGet, String expiresGet) {
        open("https://demoqa.com/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", userIDGet)); 
        getWebDriver().manage().addCookie(new Cookie("expires", expiresGet)); 
        getWebDriver().manage().addCookie(new Cookie("token", tokenGet));
        open("/profile");
        userNameLocator.shouldHave(text(expectedUserName));
    }

    @DisplayName("Проверка пустого списка после удаления книги в UI")
    public void checkDeleteBook() {
        buttonDeleteBooksLocator.click();
        windowConfirmationDeleteLocator.click();
        notBeButtonDeleteBookLocator.shouldNotBe();
    }
}
