package ru.yandex.prakticum.helpers;

import io.restassured.response.Response;
import ru.yandex.prakticum.data.User;

import static ru.yandex.prakticum.steps.UserSteps.deleteUser;
import static ru.yandex.prakticum.steps.UserSteps.registerUser;

public class UserHelpers {

    public static Response registerUniqueUser() {

        User user = generateTestUserWithUniqueEmail();

        return registerUser(user.getEmail(), user.getPassword(), user.getName());
    }

    public static User generateTestUserWithUniqueEmail() {
        return new User(
                "user_" + System.currentTimeMillis() + "@test.com",
                "password",
                "User"
        );

    }

    public static void clearUserData(String accessToken) {
        if (accessToken != null) {
            try {
                deleteUser(accessToken);
            } catch (Exception e) {
                System.err.println("Failed to delete user: " + e.getMessage());
            }
        }
    }
}
