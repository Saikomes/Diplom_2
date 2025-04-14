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
import static ru.yandex.prakticum.steps.UserSteps.*;

public class LoginUserTests extends BaseUserTests {

    @Test
    @DisplayName("Логин существующего пользователя")
    @Description("Пользователь может войти с корректными данными")
    public void checkUserLogin() {
        createTestUser();
        loginUser(email, password, name).then()
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Пользователь не может войти с неверным паролем")
    public void checkLoginWithWrongPassword() {
        createTestUser();
        loginUser(email, "wrong_" + password, name).then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин несуществующего пользователя")
    @Description("Пользователь не может войти с некорректными данными")
    public void checkNonExistUserLogin() {
        loginUser(email, password, name).then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}
