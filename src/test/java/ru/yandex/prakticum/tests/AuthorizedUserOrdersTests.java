package ru.yandex.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.prakticum.helpers.UserHelpers.clearUserData;
import static ru.yandex.prakticum.helpers.UserHelpers.registerUniqueUser;
import static ru.yandex.prakticum.steps.OrderSteps.*;

public class AuthorizedUserOrdersTests extends BaseOrderTest {
    protected String accessToken;

    @Before
    public void setUpAuth() {
        this.accessToken = registerUniqueUser()
                .jsonPath()
                .getString("accessToken");
        String[] ingredients = {validBuns.get(0), validMains.get(0)};

        createOrder(ingredients, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void createOrderWithAuth() {
        getUserOrder(accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }

    @After
    public void tearDown() {
        clearUserData(accessToken);
    }
}
