import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class chartGPTCode {

	public static void main(String[] args) {
    // Base URI
    baseURI = "https://rahulshettyacademy.com";

    // Add Place API
    String addPlaceResponse = given()
            .log().all()
            .queryParam("key", "qaclick123")
            .header("Content-Type", "application/json")

            .body("{\n" +
                    "\"location\": {\n" +
                    "\"lat\": -38.383494,\n" +
                    "\"lng\": 33.427362\n" +
                    "},\n" +
                    "\"accuracy\": 50,\n" +
                    "\"name\": \"Frontline house\",\n" +
                    "\"phone_number\": \"(+91) 983 893 3937\",\n" +
                    "\"address\": \"29, side layout, cohen 09\",\n" +
                    "\"types\": [\n" +
                    "\"shoe park\",\n" +
                    "\"shop\"\n" +
                    "],\n" +
                    "\"website\": \"http://google.com\",\n" +
                    "\"language\": \"French-IN\"\n" +
                    "}")

    .when()
            .post("/maps/api/place/add/json")

    .then()
            .assertThat()
            .statusCode(200)
            .body("scope", equalTo("APP"))
            .extract()
            .response()
            .asString();

    // Convert response to JsonPath
    JsonPath js = new JsonPath(addPlaceResponse);

    // Extract place_id
    String placeID = js.getString("place_id");

    System.out.println("Place ID : " + placeID);

    // Get Place API
    String getPlaceResponse = given()
            .log().all()
            .queryParam("key", "qaclick123")
            .queryParam("place_id", placeID)

    .when()
            .get("/maps/api/place/get/json")

    .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response()
            .asString();

    // Print response
    System.out.println(getPlaceResponse);

    // Convert response
    JsonPath js1 = new JsonPath(getPlaceResponse);

    // Get address
    String actualAddress = js1.getString("address");

    System.out.println("Address : " + actualAddress);
}
	
}
