package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.yandex.prakticum.data.Order;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.yandex.prakticum.resources.constant.Endpoints.*;

public class OrderSteps {

    @Step("Получение списка ингредиентов")
    public static Response getIngredients() {
        return given()
                .when()
                .get(INGREDIENTS);
    }

    @Step("Получение списка булок")
    public static List<String> getBuns() {
        return Arrays.asList(getIngredients()
                .jsonPath()
                .getString("data.findAll { it.type == 'bun' }._id")
                .replaceAll("[\\[\\]\"]", "")
                .replaceAll(" ", "")
                .split(","));
    }

    @Step("Получение списка основных ингредиентов")
    public static List<String> getMains() {
        return Arrays.asList(getIngredients()
                .jsonPath()
                .getString("data.findAll { it.type == 'main' }._id")
                .replaceAll("[\\[\\]\"]", "")
                .replaceAll(" ", "")
                .split(","));
    }

    @Step("Создание заказа")
    public static Response createOrder(String[] ingredients, String accessToken) {
        Order order = new Order(ingredients);

        RequestSpecification request = given()
                .body(order);

        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }

        return request
                .when()
                .post(ORDERS);
    }

    @Step("Получение заказа конкретного пользователя")
    public static Response getUserOrder(String accessToken) {
        RequestSpecification request = given();

        if (accessToken != null) {
            request.header("Authorization", accessToken);
        }

        return request
                .when()
                .get(ORDERS);
    }
}