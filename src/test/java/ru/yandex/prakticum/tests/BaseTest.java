package ru.yandex.prakticum.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.Before;

import static ru.yandex.prakticum.resources.constant.TestConfig.BASE_URI;

public class BaseTest {

    @Before
    public void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeader("Content-type", "application/json")
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .addFilter(new ErrorLoggingFilter()) // Логирование ошибок
                .build();
    }
}
