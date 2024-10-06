package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class CourierChecks {
    public void checkCreated(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");

        assertTrue(created);
    }

    @Step("Проверка неуспешного создания курьера с существующим логином")
    public void checkCreatedDuplicateCourier(ValidatableResponse createResponse) {

        boolean created = createResponse
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .path("message")
                .equals("Этот логин уже используется");

        assertTrue(created);
    }

    @Step("Неуспешное создание курьера с незаполненными обязательными полями")
    public void checkCreateCourierWithoutRequiredField(ValidatableResponse createResponse) {

        boolean created = createResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .path("message")
                .equals("Недостаточно данных для создания учетной записи");

        assertTrue(created);
    }

    @Step("Успешный логин курьера")
    public void checkLoggedIn(ValidatableResponse loginResponse) {
        int courierId = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");

        assertNotEquals(0, courierId);
    }

    @Step("Неуспешная авторизация с незаполненными полями")
    public void checkLoginCourierWithoutRequiredField(ValidatableResponse loginResponse) {
        boolean logIn = loginResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .path("message")
                .equals("Недостаточно данных для входа");

        assertTrue(logIn);
    }


    @Step("Неуспешная авторизация с несуществующей парой логин-пароль")
    public void checkLogInWithInvalidLogin(ValidatableResponse loginResponse) {
        boolean logIn = loginResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .path("message")
                .equals("Учетная запись не найдена");

        assertTrue(logIn);
    }
}
