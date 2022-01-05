package tests.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import portals.api.services.ApiClient;

import static java.lang.String.format;
import static mapper.CommonMapper.getFieldSize;
import static org.hamcrest.Matchers.equalTo;
import static utils.LoggerUtils.info;

public class FindPetSteps {

    private ApiClient client;
    private Response petsByStatusResponse;

    public FindPetSteps(){
        client = new ApiClient();
    }

    @Given("I get all the pets with {string} status")
    public void getAllPetsWithStatus(String status) {
        petsByStatusResponse = client.getPetByStatus(status);
        info(petsByStatusResponse);
    }

    @Then("I should get a {int} http status for find pet by status response")
    public void shouldGetHttpStatusForResponse(int expectedStatusCode) {
        info(petsByStatusResponse, expectedStatusCode);
    }

    @And("the get pets response contains {string} in {string} field")
    public void getPetsResponseContainsValue(String expectedValue, String fieldPath) {
        info(petsByStatusResponse);
        for (int i = 0; i < getFieldSize(petsByStatusResponse, fieldPath); i++) {
            petsByStatusResponse.then().body(format("%s[%d]", fieldPath, i), equalTo(expectedValue));
        }
    }

}
