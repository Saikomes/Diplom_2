package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
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
        registerUser(user.getEmail(), user.getPassword(), user.getName())
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
        registerUser(user.getEmail(), user.getPassword(), null)
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
        registerUser(user.getEmail(), null, user.getName())
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
        registerUser(null, user.getPassword(), user.getName())
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
        registerUser(user.getEmail(), user.getPassword(), user.getName())
                .then()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", equalTo("User already exists"));
    }

}
