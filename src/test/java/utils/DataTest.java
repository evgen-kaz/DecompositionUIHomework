package utils;

import models.AuthorizationRequestModel;

public class DataTest {
    public static final String USER_NAME = System.getProperty("userName", "Anna@Test"); //PolinaTest
    public static final String PASSWORD = System.getProperty("password", "12345!ZzAa"); //1234567890Qwerty!
    public static final String ISBN = System.getProperty("isbn", "9781449325862");

    public static final AuthorizationRequestModel AUTH_DATA = new AuthorizationRequestModel(PASSWORD, USER_NAME);
}
