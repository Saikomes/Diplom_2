package ru.yandex.prakticum.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.prakticum.steps.UserSteps.deleteUser;
import static ru.yandex.prakticum.steps.UserSteps.registerUser;

public class BaseUserTests extends BaseTest {
    protected boolean isUserCreated = false;
    protected String accessToken;
    protected String email;
    protected String password;
    protected String name;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        isUserCreated = false;
        accessToken = null;
        generateTestData();
    }

    protected void generateTestData() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        this.email = "user_" + randomString + "@test.com";
        this.password = "pass_" + randomString;
        this.name = "User_" + randomString;
    }

    protected void createTestUser() {
        Response response = registerUser(email, password, name);
        response.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
        isUserCreated = true;
    }

    @After
    public void clearUserData() {
        if (isUserCreated && accessToken != null) {
            try {
                deleteUser(accessToken);
            } catch (Exception e) {
                System.err.println("Failed to delete user: " + e.getMessage());
            }
        }
    }
}