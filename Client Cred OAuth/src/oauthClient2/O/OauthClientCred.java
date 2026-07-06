package oauthClient2.O;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.api;

public class OauthClientCred {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};

		String response = given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);

		JsonPath jasonpath = new JsonPath(response);
		String accessToken = jasonpath.getString("access_token");

		GetCourse gc = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<api> apiCourses = gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Get the course Name of WebAutomation
		ArrayList<String> a = new ArrayList<String >();
		
		List<pojo.webAutomation> wa = gc.getCourses().getWebAutomation();
		
		for(int j=0;j<wa.size();j++) {
			
			a.add(wa.get(j).getCourseTitle());
			
		System.out.println(wa.get(j).getCourseTitle());
		
		}
		
		
		List<String> expectedList = Arrays.asList(courseTitles);
		
		Assert.assertTrue(a.equals(expectedList));
	}

}
