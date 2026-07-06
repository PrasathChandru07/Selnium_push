package ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommersAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("chandraprasath8@gmail.com");
		loginRequest.setUserPassword("Chandru@1995");

		RequestSpecification reqLogin = given().spec(req).log().all().body(loginRequest);

		LoginResponse LoginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);

		// System.out.println(reqLogin);

		String EcomToken = LoginResponse.getToken();
		String userId = LoginResponse.getUserId();

		System.out.println(LoginResponse.getToken());
		System.out.println(LoginResponse.getUserId());
		System.err.println(LoginResponse.getMessage());

		// Add Product

		File productImage = new File("C:/Users/chand/OneDrive/Documents/Lap_img.png");

		System.out.println("File Exists : " + productImage.exists());
		System.out.println("Absolute Path : " + productImage.getAbsolutePath());

		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", EcomToken).build();

		String addProductResponse = given().spec(addProductBaseReq).param("productName", "Laptop")
				.param("productAddedBy", userId).param("productCategory", "Electronics")
				.param("productSubCategory", "Laptop").param("productPrice", "11500")
				.param("productDescription", "Asus 14inch laptop").param("productFor", "All")
				.multiPart("productImage", productImage).when().post("/api/ecom/product/add-product").then()
				.statusCode(201).log().body().extract().response().asString();

		System.out.println(addProductResponse);

		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.getString("productId");

		System.out.println("Product ID : " + productId);

		// Create Order

		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", EcomToken).setContentType(ContentType.JSON).build();

		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderId(productId);

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);

		Orders orders = new Orders();
		orders.setOrders(orderDetailList);

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().extract().response()
				.asString();

		System.out.println("Create an Order : " + responseAddOrder);

		// Delete Order

		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", EcomToken).setContentType(ContentType.JSON).build();

		RequestSpecification deleteProdReq = given().log().all().spec(deleteOrderBaseReq).pathParam("productId",
				productId);
		String deleteProductResponse =   deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract()
				.response().asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
	}

}
