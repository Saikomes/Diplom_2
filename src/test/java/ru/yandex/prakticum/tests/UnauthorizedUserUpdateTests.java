package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static java.net.HttpURLConnection.*;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.prakticum.steps.UserSteps.updateUser;

@RunWith(Parameterized.class)
public class UnauthorizedUserUpdateTests extends BaseUserUpdateTests {

    private final String token;
    private final String email;
    private final String name;
    private final String expectedMessage;
    private final int expectedStatusCode;

    public UnauthorizedUserUpdateTests(String token, String email, String name, String expectedMessage, int expectedStatusCode) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.expectedMessage = expectedMessage;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters(name = "Тест: токен={0}, email={1}, имя={2}, ожидаемое сообщение={3}, статус={4}")
    public static Collection<Object[]> createOrderTestData() {
        return Arrays.asList(new Object[][] {
                {"testToken", "unauthorized@test.com", null,  "You should be authorised", HTTP_UNAUTHORIZED},
                {"testToken", null, "unauthorized",  "You should be authorised", HTTP_UNAUTHORIZED},
                {"testToken", "unauthorized@test.com", "unauthorized",  "You should be authorised", HTTP_UNAUTHORIZED},
                {"Bearer token", "unauthorized@test.com", null,  "jwt malformed", HTTP_FORBIDDEN},
        });
    }

    @Test
    @DisplayName("Проверка авторизации с разными наборами данных при отсутствии авторизации")
    @Description("Тестирование различных сценариев входа с неавторизованным пользователем")
    public void testLoginWithInvalidCredentials() {
        updateUser(token, email, name)
                .then()
                .statusCode(expectedStatusCode)
                .body("message", equalTo(expectedMessage));
    }

}
