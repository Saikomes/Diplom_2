package ru.yandex.prakticum.tests;

import org.junit.After;
import org.junit.Before;

import static ru.yandex.prakticum.steps.UserSteps.*;

public class BaseUserUpdateTests extends BaseUserTests {
    protected String originalEmail;
    protected String originalName;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        createTestUser();
        // Сохраняем оригинальные данные
        this.originalEmail = user.getEmail();
        this.originalName = user.getName();
    }

    @After
    public void restoreOriginalData() {
        if (accessToken != null) {
            updateUser(accessToken, originalEmail, originalName);
        }
    }
}