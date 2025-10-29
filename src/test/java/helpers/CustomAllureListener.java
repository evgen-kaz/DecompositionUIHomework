package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured(); //взяли AllureRestAssured

    public static AllureRestAssured withCustomTemplates() { //и расширили его
        FILTER.setRequestTemplate("request.ftl"); //добавили 1-й шаблон - запроса, что в папке resources
        FILTER.setResponseTemplate("response.ftl"); //добавили 2-й шаблон - ответа, что в папке resources
        return FILTER;
    }
}