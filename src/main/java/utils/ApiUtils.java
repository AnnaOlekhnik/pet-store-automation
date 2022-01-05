package utils;

import static java.lang.String.*;

public final class ApiUtils {

    private ApiUtils() {
    }

    public static String combineUrl(String baseUrl, String urlPart) {
        return format("%s%s", baseUrl, urlPart);
    }
}