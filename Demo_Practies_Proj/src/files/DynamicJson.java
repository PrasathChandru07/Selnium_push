package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").
		body(Payload.Addbook(isbn, aisle)).when()
		.post("/Library/Addbook.php").
		then().assertThat().statusCode(200).
		extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJason(response);
		String Id = js.get("ID");
		System.out.println(Id);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		
		return new Object[][] {{"ojfwty","9363"}, {"cwetee","4253"},{"okmfet","533"}};
		}
		
	}

