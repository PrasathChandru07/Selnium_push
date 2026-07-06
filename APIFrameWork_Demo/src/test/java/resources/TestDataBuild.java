package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

	public AddPlace addPlacePayLoad(String name, String language, String address) {

		AddPlace ap = new AddPlace();

		ap.setAccuracy(50);
		ap.setName(name);
		ap.setAddress(address);
		ap.setLanguage(language);
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("https://rahulshettyacademy.com/");

		List<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shop");
		ap.setTypes(types);

		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		ap.setLocation(location);
		return ap;
	}

	public String deletePlacePayload(String placeId) {
		
		return "{\n" + "\"place_id\":\"" + placeId + "\"\n" + "}";
	}

}
