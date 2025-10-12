package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;
import static ru.yandex.prakticum.steps.UserSteps.updateUser;

public class AuthorizedUserUpdateTests extends BaseUserUpdateTests {

    @Test
    @DisplayName("Обновление email авторизованного пользователя")
    @Description("Проверка возможности обновления email авторизованного пользователя")
    public void updateAuthorizedUserEmail() {
        String newEmail = "updated_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        updateUser(accessToken, newEmail, originalName)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(newEmail.toLowerCase()))
                .body("user.name", equalTo(originalName));
    }

    @Test
    @DisplayName("Обновление имени авторизованного пользователя")
    public void updateAuthorizedUserName() {
        String newName = "Updated Name";

        updateUser(accessToken, originalEmail, newName)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo(newName))
                .body("user.email", equalTo(originalEmail));
    }

    @Test
    @DisplayName("Обновление всех данных авторизованного пользователя")
    public void updateAllAuthorizedUserData() {
        String newEmail = "fullupdate_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        String newName = "Fully Updated User";

        updateUser(accessToken, newEmail, newName)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(newEmail.toLowerCase()))
                .body("user.name", equalTo(newName));
    }
}