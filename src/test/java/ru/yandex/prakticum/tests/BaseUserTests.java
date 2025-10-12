package ru.yandex.prakticum.tests;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import ru.yandex.prakticum.data.User;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.prakticum.helpers.UserHelpers.clearUserData;
import static ru.yandex.prakticum.helpers.UserHelpers.generateTestUserWithUniqueEmail;
import static ru.yandex.prakticum.steps.UserSteps.registerUser;

public class BaseUserTests extends BaseTest {
    protected String accessToken;
    protected User user;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        accessToken = null;
        user = generateTestUserWithUniqueEmail();
    }

    protected void createTestUser() {
        Response response = registerUser(user.getEmail(), user.getPassword(), user.getName());
        response.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void tearDown() {
        clearUserData(accessToken);
    }
}