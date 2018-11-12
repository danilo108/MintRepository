package mint.com.services.customers.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document
public class Customer extends BasicResource{
	
	@Id
	private String customerId;
	
	private String name;
	private String lastName;
	private String address;
	
	public Customer(String name, String lastName, String address) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.address = address;
	}


	public Customer() {
		
	}
	
	
	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
