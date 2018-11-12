package mint.com.services.customers.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicResource {

	@Override
	public String toString() {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
		
	}
}
