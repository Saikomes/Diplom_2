package ru.yandex.prakticum.tests;

import org.junit.Before;
import ru.yandex.prakticum.steps.OrderSteps;

import java.util.List;

public class BaseOrderTest extends BaseTest {
    protected List<String> validBuns;
    protected List<String> validMains;

    @Before
    public void setUpIngredients() {
        this.validBuns = OrderSteps.getBuns();
        this.validMains = OrderSteps.getMains();

        System.out.println("Available buns: " + validBuns.size());
        System.out.println("Available mains: " + validMains.size());
    }
}