package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.prakticum.steps.UserSteps.*;

public class LoginUserTests extends BaseUserTests {

    @Test
    @DisplayName("Логин существующего пользователя")
    @Description("Пользователь может войти с корректными данными")
    public void checkUserLogin() {
        createTestUser();
        loginUser(user.getEmail(), user.getPassword(), user.getName()).then()
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
        loginUser(user.getEmail(), "wrong" + user.getPassword(), user.getName()).then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин несуществующего пользователя")
    @Description("Пользователь не может войти с некорректными данными")
    public void checkNonExistUserLogin() {
        loginUser(user.getEmail(), user.getPassword(), user.getName()).then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}
