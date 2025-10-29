package utils;

import models.AuthorizationRequestModel;

public class DataTest {
    public static final String USER_NAME = System.getProperty("userName", "Anna@Test");
    public static final String PASSWORD = System.getProperty("password", "12345!ZzAa");
    public static final String ISBN = System.getProperty("isbn", "9781449325862");

    public static final AuthorizationRequestModel AUTH_DATA = new AuthorizationRequestModel(PASSWORD, USER_NAME);
}
