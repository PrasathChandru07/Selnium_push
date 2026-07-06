package api_Automation_Practies;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods; 

public class Basics {
	

	public static void main(String[] args) throws IOException {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String (Files.readAllBytes(Paths.get("C:\\Users\\chand\\Downloads\\Add_Place.json")))).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		System.err.println(response);
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
//Update Place
		
		String newAddress = "Pacfic ocean";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
//Get Place
		baseURI = "https://rahulshettyacademy.com";
		
		String getPlaceResponse = given().log().all().queryParam("key","qaclick123").queryParam( "place_id",placeID).
		when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath js1 = ReUsableMethods.rawToJason(getPlaceResponse);
		String actualAddress = js1.get("address");
		
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, "Pacfic ocean");
			
	}

}
