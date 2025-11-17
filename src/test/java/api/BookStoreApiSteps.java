package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import models.AddBookRequestModel;
import models.AddBookResponseModel;
import models.AddIsbnRequestModel;
import org.junit.jupiter.api.DisplayName;
import pages.ProfilePage;

import java.util.List;

import static api.EndPoint.BOOKS_END_POINT;
import static api.EndPoint.BOOKS_WITH_USER_ID;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.BaseSpecs.*;
import static specs.BaseSpecs.getAuthRequestSpec;
import static utils.DataTest.ISBN;

public class BookStoreApiSteps {
    ProfilePage profilePage = new ProfilePage();
    public String jsonBody;

    @DisplayName("Удаление всех книг")
    public void deleteBooks(String tokenGet, String userIDGet) {
        RestAssured.defaultParser = Parser.JSON;
                given(getAuthRequestSpec(tokenGet))
                        .when()
                        .delete(BOOKS_WITH_USER_ID + userIDGet)
                        .then()
                        .spec(responseSpec(204));
    }

    @DisplayName("Добавление книги")
    public AddBookResponseModel addBook(String tokenGet) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AddIsbnRequestModel isbnModel = new AddIsbnRequestModel();
        isbnModel.setIsbn(ISBN);

        AddBookRequestModel addBookModel = new AddBookRequestModel();
        addBookModel.setUserId("a11b9d00-d415-4099-84bc-485592546bf9");
        addBookModel.setCollectionOfIsbns(List.of(isbnModel));
        jsonBody = mapper.writeValueAsString(addBookModel);

        AddBookResponseModel responseAddBookModel =
                given(getAuthRequestSpec(tokenGet))
                        .body(jsonBody)
                        .when()
                        .post(BOOKS_END_POINT)
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(AddBookResponseModel.class);
        step("Проверка добавления книги через API", () -> {
            assertEquals(List.of(isbnModel), responseAddBookModel.getBooks());
        });
        return responseAddBookModel;
    }

    @DisplayName("Удаление книги")
    public void deleteBook(String tokenGet, String userIDGet){
        given(getAuthRequestSpec(tokenGet))
                .body(jsonBody)
                .when()
                .delete(BOOKS_WITH_USER_ID + userIDGet)
                .then()
                .spec(responseSpec(204));
    }
}
