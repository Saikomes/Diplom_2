package ru.yandex.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.prakticum.steps.OrderSteps.createOrder;
import static ru.yandex.prakticum.helpers.UserHelpers.*;

public class AuthorizedOrderTests extends BaseOrderTest {
    protected String accessToken;

    @Before
    public void setUpAuth() {
        this.accessToken = registerUniqueUser()
                .jsonPath()
                .getString("accessToken");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithAuth() {
        String[] ingredients = {validBuns.get(0), validMains.get(0)};

        createOrder(ingredients, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без ингредиентов")
    public void createOrderWithAuthWithoutIngredients() {
        createOrder(new String[]{}, accessToken)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithAuthWithWrongHash() {
        createOrder(new String[]{"wrong1", "wrong2"}, accessToken)
                .then()
                .statusCode(HTTP_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        clearUserData(accessToken);
    }
}
