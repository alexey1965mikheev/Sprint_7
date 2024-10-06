package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

public class OrdersChecks {

    @Step("Проверка создания ордера")
    public void checkCreated(ValidatableResponse createResponse) {
        int track = createResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("track");

        assertNotEquals(0, track);
    }

    @Step("Проверка получения списка ордеров")
    public void checkGetOrders(ValidatableResponse Response) {
        String getOrders = Response
                .assertThat()
                .statusCode(HTTP_OK)
                .body(Matchers.anything())
                .extract()
                .body()
                .toString();

        assertFalse(getOrders.isEmpty());
    }


}
