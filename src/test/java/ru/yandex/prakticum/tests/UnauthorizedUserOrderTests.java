package ru.yandex.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.prakticum.steps.OrderSteps.getUserOrder;

public class UnauthorizedUserOrderTests extends BaseOrderTest {

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void createOrderWithoutAuth() {
        getUserOrder(null)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }
}
