package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import extensions.LoginExtensions;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.AddBookRequestModel;
import models.AddBookResponseModel;
import models.AddIsbnRequestModel;
import models.DeleteBookRequestModel;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.BaseSpecs.*;
import static specs.BaseSpecs.getAuthRequestSpec;
import static utils.DataTest.GENERATE_ISBN;
import static utils.DataTest.ISBN;

public class BookStoreApiSteps {
    public static String createAddBookJson(String userId, String isbn) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(isbn);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId(userId);
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));

        return mapper.writeValueAsString(addBookModel);
    }

    @Step("Отправка DELETE-запроса на удаление всех книг")
    public void deleteBooks(String tokenGet, String userIDGet) {
        RestAssured.defaultParser = Parser.JSON;
        given(getAuthRequestSpec(tokenGet))
                .when()
                .delete("/BookStore/v1/Books?UserId=" + userIDGet)
                .then()
                .spec(responseSpec(204));
    }

    @Step("Отправка POST-запроса на добавление книги")
    public AddBookResponseModel addBook(String tokenGet) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));
        String jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel responseAddBookModel =
                given(getAuthRequestSpec(tokenGet))
                        .body(jsonBody)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class);
        step("Проверка добавления книги через API", () -> {
            assertEquals(List.of(isbnModel), responseAddBookModel.getBooks());
        });
        return responseAddBookModel;
    }

    @Step("Отправка DELETE-запроса на удаление книги")
    public void deleteBook(String tokenGet, String userIDGet) throws JsonProcessingException {
        String jsonBody = createAddBookJson("a11b9d00-d415-4099-84bc-485592546bf9", ISBN);

        given(getAuthRequestSpec(tokenGet))
                .body(jsonBody)
                .when()
                .delete("/BookStore/v1/Books?UserId=" + userIDGet)
                .then()
                .spec(responseSpec(204));
    }

    @Step("Авторизация с помощью '@WithLogin' и отправка DELETE-запроса на удаление всех книг")
    public void AutWithAnnotationAndDeleteBooks() {
        String token = LoginExtensions.token;
        String userId = LoginExtensions.userID;
        RestAssured.defaultParser = Parser.JSON;
        given(getAuthRequestSpec(token))
                .when()
                .delete("/BookStore/v1/Books?UserId=" + userId)
                .then()
                .spec(responseSpec(204));
    }

    @Step("Отправка POST-запроса на добавление книги с данными авторизации '@WithLogin'")
    public void AutWithAnnotationAndAddBook() throws JsonProcessingException {
        String token = LoginExtensions.token;
        ObjectMapper mapper = new ObjectMapper();
        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));
        String jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel response =
                given(getAuthRequestSpec(token))
                        .body(jsonBody)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class);
        step("Проверка ответа на добавление книги", () -> {
            assertEquals(List.of(isbnModel), response.getBooks());
        });
    }

    @Step("Отправка DELETE-запроса на удаление книги с данными авторизации '@WithLogin'")
    public void AutWithAnnotationAndDeleteBook() throws JsonProcessingException {
        String token = LoginExtensions.token;
        String userId = LoginExtensions.userID;
        String jsonBody = createAddBookJson("a11b9d00-d415-4099-84bc-485592546bf9", ISBN);

        given(getAuthRequestSpec(token))
                .body(jsonBody)
                .when()
                .delete("/BookStore/v1/Books?UserId=" + userId)
                .then()
                .spec(responseSpec(204));
    }

    @Step("Отправка DELETE-запроса на удаление книги пользователем с просроченным токеном")
    public void unsuccessfulDeleteBook(String tokenGet, String userIDGet) throws JsonProcessingException {
        String jsonBody = createAddBookJson("a11b9d00-d415-4099-84bc-485592546bf9", ISBN);

        given(getAuthRequestSpec(tokenGet))
                .body(jsonBody)
                .when()
                .delete("/BookStore/v1/Books?UserId=" + userIDGet)
                .then()
                .spec(responseSpec(401));
    }

    @Step("Отправка DELETE-запроса на удаление несуществующей книги")
    public void unsuccessfulDeleteNonExistentBook(String tokenGet, String userIDGet) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DeleteBookRequestModel deleteBookRequestModel = new DeleteBookRequestModel();
        deleteBookRequestModel.setIsbn(GENERATE_ISBN);
        deleteBookRequestModel.setUserId(userIDGet);
        String jsonBody = mapper.writeValueAsString(deleteBookRequestModel);

        given(getAuthRequestSpec(tokenGet))
                .body(jsonBody)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec(400));
    }
}