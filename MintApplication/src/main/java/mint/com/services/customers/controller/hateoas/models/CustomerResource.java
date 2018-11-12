package mint.com.services.customers.controller.hateoas.models;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import mint.com.services.customers.model.Customer;

public class CustomerResource extends ResourceSupport {

	@JsonUnwrapped
	Customer customer;

	public CustomerResource() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
