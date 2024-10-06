package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrdersClient {

    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String ORDER_PATH = "/api/v1/orders";

    @Step("Запрос на создание ордера")
    public ValidatableResponse createOrder(Orders order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Запрос на получение списка ордеров")
    public ValidatableResponse getOrders() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    @Step("Запрос на удаление ордера")
    public ValidatableResponse deleteOrder(int orderTrack) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(Map.of("track", orderTrack))
                .when()
                .put(ORDER_PATH + "/cancel")
                .then().log().all();
    }
}
