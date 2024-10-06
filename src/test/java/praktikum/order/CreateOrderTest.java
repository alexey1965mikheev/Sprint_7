package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)

public class CreateOrderTest {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;

    public CreateOrderTest(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    private OrdersClient client = new OrdersClient();
    private OrdersChecks check = new OrdersChecks();
    private int orderTrack;

    @Before
    @Step("Подготовка тестовых данных")
    public void preparingTestData() {
        this.firstName = "Александр";
        this.lastName = "Александров";
        this.address = "Усачева, 3";
        this.metroStation = "12";
        this.phone = "+7 (927) 775-03-03";
        this.rentTime = 2;
        this.deliveryDate = "2024-10-10";
        this.comment = "Комментарий";
    }
    @After
    @Step("Удаление данных после теста")
    public void deleteOrder() {
        if (orderTrack != 0) {
            client.deleteOrder(orderTrack);
        }
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Test
    public void order() {

        var order = new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, scooterColor);
        ValidatableResponse createResponse = client.createOrder(order);
        check.checkCreated(createResponse);
    }
}
