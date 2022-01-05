package tests.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import portals.api.services.ApiClient;
import portals.api.services.requests.PetRequest;

import static mapper.CommonMapper.getOrderField;
import static org.hamcrest.Matchers.equalTo;
import static utils.LoggerUtils.info;
import static utils.RandomUtils.generatePetName;

public class UpdatePetsSteps {

    private ApiClient client;
    private Response newPetResponse;
    private Response getPetByIdResponse;
    private Response updatePetByIdResponse;
    private Response deletePetResponse;

    public UpdatePetsSteps() {
        client = new ApiClient();
    }

    @When("I cal post pet endpoint to add new pet")
    public void addNewPet() {

        String[] urls = new String[]{"Url1", "Url2"}; //could be taken from Db or file
        PetRequest request = new PetRequest()
                .setName(generatePetName())
                .setPhotoUrls(urls);

        newPetResponse = client.addNewPet(request);
        getPetByIdResponse = client.waitUntilGetPetStatusOk(getOrderField(newPetResponse, "id"));
    }

    @Then("I should get a {int} http status for add pet response")
    public void shouldGetStatusForResponse(int expectedStatusCode) {
        info(newPetResponse, expectedStatusCode);
    }

    @When("I call Get Pet by id endpoint using id from previous response")
    public void callGetPetsByIdEndpoint() {
        String id = getOrderField(newPetResponse, "id");
        getPetByIdResponse = client.getPetById(id);
    }

    @Then("I should get a {int} http status for get pet by id response")
    public void shouldGetStatusForGetResponse(int expectedStatusCode) {
        info(getPetByIdResponse, expectedStatusCode);
    }

    @And("the get pet response contains {string} in {string} field")
    public void getPetResponseContainsValue(String expectedValue, String fieldPath) {
        info(getPetByIdResponse);
        getPetByIdResponse.then().body(fieldPath, equalTo(expectedValue));
    }

    @Given("I call update pet endpoint to change pet status to {string}")
    public void updateThePetStatus(String newStatus) {
        String id = getOrderField(newPetResponse, "id");
        updatePetByIdResponse = client.updatePetStatus(id, newStatus);
    }

    @Then("I should get a {int} status code for update pet by id response")
    public void shouldGetStatusForUpdateResponse(int expectedStatusCode) {
        info(updatePetByIdResponse, expectedStatusCode);
    }

    @When("I delete created previously pet")
    public void deleteCreatedPet(){
        String id = getOrderField(updatePetByIdResponse, "id");
        deletePetResponse = client.deletePet(id);

    }

}
