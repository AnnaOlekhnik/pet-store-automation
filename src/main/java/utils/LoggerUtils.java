package utils;

import io.restassured.response.Response;

public final class LoggerUtils {

    private LoggerUtils() {
    }

    public static void info(Response response) {
        response.then().log().all();
    }

    public static void info(Response response, int statusCode) {
        response.then().log().all().statusCode(statusCode);
    }
}