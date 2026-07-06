 	package stepDesinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add Place Payload {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {

		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		APIResources resourceAPI = APIResources.valueOf(resource);

		System.out.println("Resource : " + resourceAPI.getResources());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST")) {

			response = res.when().post(resourceAPI.getResources());

		} else if (method.equalsIgnoreCase("GET")) {

			response = res.when().get(resourceAPI.getResources());

		}

		System.out.println("====================================");
		System.out.println("Status Code : " + response.getStatusCode());
		System.out.println("Response Body : ");
		System.out.println(response.asPrettyString());
		System.out.println("====================================");
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer statusCode) {

		assertEquals(statusCode.intValue(), response.getStatusCode());
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {

		String actualValue = getJsonPath(response, key);

		System.out.println("Expected : " + expectedValue);
		System.out.println("Actual   : " + actualValue);

		assertEquals(expectedValue, actualValue);
	}

	@Then("{string} in resonse body is {string}")
	public void in_resonse_body_is(String key, String expectedValue) {

		String actualValue = getJsonPath(response, key);

		assertEquals(expectedValue, actualValue);
	}

	@Then("verify place Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {

		place_id = getJsonPath(response, "place_id");

		res = given().spec(requestSpecification()).queryParam("place_id", place_id);

		user_calls_with_http_request(resource, "GET");

		String actualName = getJsonPath(response, "name");

		assertEquals(expectedName, actualName);
	}

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {

		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}