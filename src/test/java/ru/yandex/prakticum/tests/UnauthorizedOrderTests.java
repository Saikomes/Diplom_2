package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.prakticum.steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class UnauthorizedOrderTests extends BaseOrderTest {

    private final String[] ingredients;

    public UnauthorizedOrderTests(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Test
    @DisplayName("Проверка создания заказов с разными наборами данных при отсутствии авторизации")
    public void createOrderWithoutAuth() {
        createOrder(ingredients, null)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Parameterized.Parameters
    public static List<Object[]> unauthorizedCases() {
        return Arrays.asList(
                new Object[]{new String[]{ "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"}},
                new Object[]{new String[]{}},
                new Object[]{new String[]{"wrong1", "wrong2"}},
                new Object[]{null}
        );
    }
}