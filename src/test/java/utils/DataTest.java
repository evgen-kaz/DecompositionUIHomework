package utils;

import models.AuthorizationRequestModel;

import java.util.Random;

public class DataTest {
    public static final String USER_NAME = System.getProperty("userName", "AlexIvanov");
    public static final String PASSWORD = System.getProperty("password", "1234567890Qwerty!");

    public static final String EXPIRED_TOKEN_USER_NAME = System.getProperty("userName", "PolinaTest"); 
    public static final String EXPIRED_TOKEN_PASSWORD = System.getProperty("password", "1234567890Qwerty!");

    public static final String ISBN = System.getProperty("isbn", "9781449325862");
    public static final String GENERATE_ISBN = System.getProperty("isbn", generateIsbn());

    public static final AuthorizationRequestModel AUTH_DATA = new AuthorizationRequestModel(PASSWORD, USER_NAME);
    public static final AuthorizationRequestModel AUTH_DATA_WITH_EXPIRED_TOKEN = new AuthorizationRequestModel(EXPIRED_TOKEN_PASSWORD, EXPIRED_TOKEN_USER_NAME);


    public static String generateIsbn() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i <= 13; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
}
