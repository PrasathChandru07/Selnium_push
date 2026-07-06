package stepDesinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before()
	public void beforeSceanrio() throws IOException {

		StepDefination m = new StepDefination();
		
		if (StepDefination.place_id == null) {
			m.add_place_payload("Chandru", "Tamilnadu", "India");
			m.user_calls_with_http_request("AddPLaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("Chandru", "getPlaceAPI");

		}
	}

}
