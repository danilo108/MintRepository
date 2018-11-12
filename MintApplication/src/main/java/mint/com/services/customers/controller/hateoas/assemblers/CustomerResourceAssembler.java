package mint.com.services.customers.controller.hateoas.assemblers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import mint.com.services.customers.controller.CustomersController;
import mint.com.services.customers.controller.hateoas.models.CustomerResource;
import mint.com.services.customers.model.Customer;

public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

	public CustomerResourceAssembler() {
		super(CustomersController.class, CustomerResource.class);
	}

	@Override
	public CustomerResource toResource(Customer customer) {
		CustomerResource customerResource = new CustomerResource();
		if(customer != null) {
			 customerResource = instantiateResource(customer);
			 customerResource.setCustomer(customer);
		}
		
		Link self = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getCustomer(customer.getCustomerId())).withSelfRel();
		customerResource.add(self);
		customerResource.add(getGetAllLink());
		return customerResource;
	}
	
	public Link getGetAllLink() {
		return  ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getAll()).withRel("all");
	}
	
	public Link getGetAllPageableLink() {
		return ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getAll(0, 10)).withRel("all first page");
	}
	

}
