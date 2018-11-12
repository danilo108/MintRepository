package mint.com.services.customers.controller.hateoas.models;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerList extends ResourceSupport {

	public CustomerList() {
		super();
	}
	private List<CustomerResource> list;

	public CustomerList(List<CustomerResource> resources) {
		this.list = resources;
	}

	public List<CustomerResource> getList() {
		return list;
	}

	public void setList(List<CustomerResource> list) {
		this.list = list;
	}
	
	
}
