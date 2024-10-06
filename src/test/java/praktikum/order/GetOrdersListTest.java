package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class GetOrdersListTest {

    private OrdersClient client = new OrdersClient();
    private OrdersChecks check = new OrdersChecks();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Успешный запрос без courierId")
    public void getOrdersListWithoutParameters() {

        ValidatableResponse getOrdersResponse = client.getOrders();
        check.checkGetOrders(getOrdersResponse);
    }
}
