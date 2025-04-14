package ru.yandex.prakticum.helpers;

import io.restassured.response.Response;

import static ru.yandex.prakticum.steps.UserSteps.registerUser;

public class UserHelpers {

    public static Response registerUniqueUser() {
        String email = "user_" + System.currentTimeMillis() + "@test.com";
        String password = "password";
        String name = "User";

        return registerUser(email, password, name);
    }
}
