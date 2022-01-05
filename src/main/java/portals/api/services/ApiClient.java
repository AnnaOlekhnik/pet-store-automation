package portals.api.services;

import base.BaseApi;
import configuration.GlobalConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.time.StopWatch;
import portals.api.services.requests.PetRequest;


import static io.restassured.http.ContentType.JSON;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static utils.ApiUtils.combineUrl;
import static utils.LoggerUtils.info;
import static utils.PropertyUtils.getProperty;

public class ApiClient implements BaseApi {

    private static final String BASE_URL = getProperty("base_url");
    public static final int LONG_TIMEOUT = 120000; // 120 sec
    public static final int POLLING = 3000; // 3 sec


    private static final String API_KEY_HEADER = "api-key";

    private static final String GET_PET_BY_STATUS = getProperty("pet.get-by-status");
    private static final String GET_PET_BY_ID = getProperty("pet.get-by-id");
    private static final String POST_PET = getProperty("pet.post-pet");


    public Response getPetByStatus(String status){
        return getRequestSpecification()
                .header("api-key", "special-key")
                .contentType(JSON)
                .queryParam("status", status)
                .log().all()
                .get(combineUrl(BASE_URL, GET_PET_BY_STATUS));
    }

    public Response getPetById(String id){
        return getRequestSpecification()
                .header(API_KEY_HEADER, "special-key")
                .contentType(JSON)
                .pathParam("id", id)
                .log().all()
                .get(combineUrl(BASE_URL, GET_PET_BY_ID));
    }

    public Response addNewPet(PetRequest request){
        return getRequestSpecification()
                .header(API_KEY_HEADER, "special-key")
                .contentType(JSON)
                .body(request)
                .log().all()
                .post(combineUrl(BASE_URL, POST_PET));
    }

    public Response updatePetStatus(String id, String status){
        return getRequestSpecification()
                .header(API_KEY_HEADER, "special-key")
                .contentType(JSON)
                .pathParam("id", id)
                .formParam("status", status)
                .log().all()
                .post(combineUrl(BASE_URL, GET_PET_BY_ID));
    }

    public Response deletePet(String id){
        return getRequestSpecification()
                .header(API_KEY_HEADER, "special-key")
                .contentType(JSON)
                .pathParam("id", id)
                .log().all()
                .delete(combineUrl(BASE_URL, GET_PET_BY_ID));
    }

    public Response waitUntilGetPetStatusOk(String orderId) {
        Response response = null;

        try {
            StopWatch timer = new StopWatch();

            timer.start();
            while (timer.getTime() < LONG_TIMEOUT) {
                response = getPetById(orderId);
                info(response);

                if (response.getStatusCode() == 200) {
                    break;
                }

                sleep(POLLING);
            }
            timer.stop();

            // Fail test due to Timeout exceeded
            if (!(response.getStatusCode() == 200)){
                throw new AssertionError(format("Timeout has been exceeded: [%d]", LONG_TIMEOUT));
            }
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }

        return response;
    }
}
