package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.given;

public class CreateCourierTest {

    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    private Courier courier;

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courier == null) return;
        CourierCredentials cred = CourierCredentials.fromCourier(courier);
        int courierId = client.logIn(cred).extract().path("id");
        if (courierId != 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Успешное создание учетной записи")
    public void createCourier() {

        courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Проверка запроса с повторяющимся логином")
    public void createDuplicateCourier() {

        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        createResponse = client.createCourier(courier);
        check.checkCreatedDuplicateCourier(createResponse);
    }

    @Test
    @DisplayName("Создание курьера с незаполненным полем Login")
    @Description("Проверка обязательности заполнения полей")
    public void createCourierWithEmptyLoginField() {

        Courier courier = new Courier("","password" + RandomStringUtils.randomAlphanumeric(8), "name"+ RandomStringUtils.randomAlphanumeric(8));
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(createResponse);
    }

    @Test
    @DisplayName("Создание курьера с незаполненным полем Login")
    @Description("Проверка обязательности заполнения полей")
    public void createCourierWithEmptyPasswordField() {

        Courier courier = new Courier("loin" + RandomStringUtils.randomAlphanumeric(8), "", "name"+ RandomStringUtils.randomAlphanumeric(8));
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(createResponse);
    }

    @Test
    @DisplayName("Создание курьера без параметров")
    @Description("Проверка обязательности заполнения полей")
    public void createCourierWithoutParameters() {

        Courier courier = new Courier("", "", "");
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreateCourierWithoutRequiredField(createResponse);
    }

}

