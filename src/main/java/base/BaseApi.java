package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public interface BaseApi {

    default RequestSpecification getRequestSpecification() {
        return given().filter(new AllureRestAssured()).log().all();
    }
}