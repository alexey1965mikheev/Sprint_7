package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {

    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    private CourierCredentials cred;

    @Before
    public void preparingTestData() {
        Courier courier = Courier.random();
        cred = CourierCredentials.fromCourier(courier);
        client.createCourier(courier);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        ValidatableResponse validatableResponse = client.logIn(cred);
        int courierId = validatableResponse.extract().path("id");
        if (courierId != 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Успешный логин курьера с валидными параметрами")
    public void loginCourier() {
        ValidatableResponse loginResponse = client.logIn(cred);
        check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Логин курьера без входных параметров")
    @Description("Проверка обязательности заполнения полей")
    public void loginCourierWithoutParameters() {

        ValidatableResponse loginResponse = client.logIn(new CourierCredentials("", ""));
        check.checkLoginCourierWithoutRequiredField(loginResponse);
    }

    @Test
    @DisplayName("Логин курьера с незаполненным полем login")
    @Description("Проверка обязательности заполнения полей")
    public void loginCourierWithEmptyLogin() {

        ValidatableResponse loginResponse = client.logIn(new CourierCredentials("", "password" + RandomStringUtils.randomAlphanumeric(8)));
        check.checkLoginCourierWithoutRequiredField(loginResponse);
    }

    @Test
    @DisplayName("Логин курьера с незаполненным полем password")
    @Description("Проверка обязательности заполнения полей")
    public void loginCourierWithEmptyPassword() {

        ValidatableResponse loginResponse = client.logIn(new CourierCredentials("login" + RandomStringUtils.randomAlphanumeric(8), ""));
        check.checkLoginCourierWithoutRequiredField(loginResponse);
    }

    @Test
    @DisplayName("Авторизация с некорректным логином")
    @Description("Проверка запроса с несуществующей парой логин-пароль")
    public void loginCourierWithInvalidLogin() {
        ValidatableResponse loginResponse = client.logIn(new CourierCredentials("login/" + RandomStringUtils.randomAlphanumeric(8), "password" + RandomStringUtils.randomAlphanumeric(8)));
        check.checkLogInWithInvalidLogin(loginResponse);
    }
}
