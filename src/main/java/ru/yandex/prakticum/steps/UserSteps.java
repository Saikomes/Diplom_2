package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.yandex.prakticum.data.User;

import static io.restassured.RestAssured.given;
import static ru.yandex.prakticum.resources.constant.Endpoints.*;

public class UserSteps {


    @Step("Регистрация пользователя, POST /api/auth/register")
    public static Response registerUser(String email, String password, String name) {
        var user = new User(email, password, name);
        return given()
                .body(user)
                .when()
                .post(REGISTER_USER)
                .then()
                .extract().response();
    }


    @Step("Удаляем пользователя, DELETE /api/auth/user")
    public static void deleteUser(String email, String password, String name) {
        // Сначала получаем ответ от логина курьера
        Response loginResponse = loginUser(email, password, name);

        // Извлекаем ID из ответа
        String accessToken = loginResponse.jsonPath().getString("accessToken").split(" ")[1];

        if(accessToken != null) {
            given()
                    .auth()
                    .oauth2(accessToken)
                    .when()
                    .delete(INFO_USER)
                    .then()
                    .extract().response();
        }
    }

    @Step("Удаляем пользователя, DELETE /api/auth/user")
    public static void deleteUser(String accessToken) {
        RequestSpecification request = given();
        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }
        request
                .when()
                .delete(INFO_USER);
    }

    @Step("Логин пользователя. POST /api/auth/login")
    public static Response loginUser(String email, String password, String name) {
        var user = new User(email, password, name);
        return given()
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then()
                .extract().response();
    }

    @Step("Изменение данных пользователя. PATCH /api/auth/user")
    public static Response updateUser(String accessToken, String email, String name) {
        var user = new User(email, null, name);
        RequestSpecification request = given()
                .body(user);
        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }
        return request
                .when()
                .patch(INFO_USER)
                .then()
                .extract().response();
    }


}
