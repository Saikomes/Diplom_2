package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.prakticum.resources.constant.UserTestData.TEST_NAME;
import static ru.yandex.prakticum.steps.UserSteps.deleteUser;
import static ru.yandex.prakticum.steps.UserSteps.registerUser;

public class CreateUserTests extends BaseUserTests {

    @Test
    @DisplayName("Создание пользователя")
    @Description("Пользователя можно создать с корректными данными")
    public void checkUserCreation() {
        createTestUser();
    }

    @Test
    @DisplayName("Два одинаковых пользователя")
    @Description("Два одинаковых курьера должно быть запрещено создавать")
    public void checkCanNotCreateTwoTheSameUsers() {
        createTestUser();
        registerUser(email, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя с отстутствующим именем")
    @Description("Создание пользователя с отстутствующим именем запрещено")
    public void checkUserCreatedWithMandatoryFields() {
        registerUser(email, password, null)
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("В случае отсутствия пароля получаем ошибку")
    public void checkErrorIfBodyHasNoPssField() {
        registerUser(email, null, name)
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("В случае отсутствия email получаем ошибку")
    public void checkErrorIfBodyHasNoLoginField() {
        registerUser(null, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя с существующим email")
    @Description("При попытке создать пользователя с существующим email получаем ошибку")
    public void checkErrorForUserCreationWithExistingLogin() {
        createTestUser();
        registerUser(email, password + "test", TEST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"));
    }

}
