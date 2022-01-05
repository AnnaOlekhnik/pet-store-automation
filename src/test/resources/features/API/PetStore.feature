@api
Feature: DemoPetStore

  Scenario: Get "available" pets and verify result
    Given I get all the pets with "available" status
    Then I should get a 200 http status for find pet by status response
    And the get pets response contains "available" in "status" field

  Scenario: Post a new available pet to the store, update and remove
    Given I cal post pet endpoint to add new pet
    Then I should get a 200 http status for add pet response
    When I call Get Pet by id endpoint using id from previous response
    Then I should get a 200 http status for get pet by id response
    When I call update pet endpoint to change pet status to "sold"
    Then I should get a 200 status code for update pet by id response
    When I delete created previously pet
    And I call Get Pet by id endpoint using id from previous response
    Then I should get a 404 http status for get pet by id response
    And the get pet response contains "Pet not found" in "message" field



