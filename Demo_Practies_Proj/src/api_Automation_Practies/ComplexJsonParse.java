package api_Automation_Practies;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
//Print No of Courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
//Print Purchase Amount
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
//Print Title of the 1st Course
		
		String titleFirstCourse= js.get("courses[1].title");
		System.out.println(titleFirstCourse);
		
// Print All Course Title and their respective prices
		
		for(int i=0;i<count;i++) {
			String courseTitle= js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitle);
		}
		
		System.out.println("Print no of copies sold by RPA Course");
		
		for(int i=0;i<count;i++) { 
			{
				String courseTitles = js.get("courses["+i+"].title");

				if(courseTitles.equalsIgnoreCase("RPA")) 
				{
					int copies = js.getInt("courses["+i+"].copies");
					System.out.println(copies);
					break;
				}
			}
		}
	}
}
		


