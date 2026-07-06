Feature: Validation place API's

@AddPlace @Regression
  Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI
    Given Add Place Payload "<name>" "<language>" "<address>"
    When user calls "AddPLaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in resonse body is "APP"
    And verify place Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name    | language | address            |
      | Chandru | English  | World cross center |

  # | Prasath | Tamil    | Tamilnadu cross center |
  
  
  @DeletePlace @Regression
  Scenario: Verify if Delete Place functionality is working
    Given DeletePlace Payload
    When user calls "deletePlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
