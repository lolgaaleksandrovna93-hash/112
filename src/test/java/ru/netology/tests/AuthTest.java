package ru.netology.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private void login(String login, String password) {
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }

    // успешный вход в ibank — это переход на страницу личного кабинета
    private void assertSuccess() {
        $("h2")
                .shouldBe(Condition.visible, TIMEOUT)
                .shouldHave(Condition.text("Личный кабинет"));
    }

    private void assertError(String message) {
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(Condition.visible, TIMEOUT)
                .shouldHave(Condition.text(message));
    }

    @Test
    void shouldLoginActiveUserWithValidCredentials() {
        RegistrationDto user = DataGenerator.getRegisteredUser("active");
        login(user.getLogin(), user.getPassword());
        assertSuccess();
    }

    @Test
    void shouldNotLoginBlockedUser() {
        RegistrationDto user = DataGenerator.getRegisteredUser("blocked");
        login(user.getLogin(), user.getPassword());
        assertError("Ошибка! Пользователь заблокирован");
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        RegistrationDto user = DataGenerator.getRegisteredUser("active");
        login(DataGenerator.generateLogin(), user.getPassword());
        assertError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        RegistrationDto user = DataGenerator.getRegisteredUser("active");
        login(user.getLogin(), DataGenerator.generatePassword());
        assertError("Ошибка! Неверно указан логин или пароль");
    }
}
