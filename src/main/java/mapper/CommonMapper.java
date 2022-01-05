package mapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static java.lang.String.format;

public class CommonMapper {

    private CommonMapper() {
    }

    public static String getOrderField(Response response, String field) {
        return new JsonPath(response.asString())
                .getString(field);
    }

    public static int getFieldSize(Response response, String fieldName) {
        return new JsonPath(response.asString())
                .getInt(format("%s.size()", fieldName));
    }
}
