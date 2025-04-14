package ru.yandex.prakticum.resources.constant;

import java.time.LocalDateTime;

public class OrderTestData {
    public static final String TEST_FIRST_NAME = "Naruto";
    public static final String TEST_LAST_NAME = "Uchiha";
    public static final String TEST_ADDRESS = "Konoha, 142 apt.";
    public static final String TEST_METRO_STATION = "4";
    public static final String TEST_DELIVERY_DATE = LocalDateTime.now().plusHours(1).toString();
    public static final String TEST_PHONE = "+7 800 355 35 35";
    public static final int TEST_RENT_TIME = 5;
    public static final String TEST_COMMENT = "Saske, come back to Konoha";
    public static final String TEST_COLOR_BLACK = "BLACK";
    public static final String TEST_COLOR_GREY = "GREY";
}
