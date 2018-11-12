package mint.com.services.customers.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mint.com.services.customers.controller.exceptions.MintRuntimeException;
import mint.com.services.customers.controller.exceptions.MintRuntimeExceptionFactory;
import mint.com.services.customers.controller.hateoas.assemblers.CustomerResourceAssembler;
import mint.com.services.customers.controller.hateoas.models.CustomerList;
import mint.com.services.customers.controller.hateoas.models.CustomerResource;
import mint.com.services.customers.model.Customer;
import mint.com.services.customers.model.repositories.CustomerRepository;
import mint.com.services.customers.service.CustomerService;

/**
 * 
 * THe customers controller
 * @author Danilo
 *
 */
@RestController()
public class CustomersController {
	private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);

	private final CustomerService customerService;
	@Autowired
	MintRuntimeExceptionFactory exceptionFactory;

	@Value("${controllers.customers.validation.400.getAllPageable}")
	private String getAllPageableParamValidation;
	@Autowired
	CustomerRepository customerRepository;
	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Finds all the customers returning a pageable result
	 * @param pageNumber the page number to return
	 * @param pageSize the number of Customers per page
	 * @return The Customer for the given page
	 */
	@RequestMapping(path = { "/customers" }, method = RequestMethod.GET)
	public CustomerList getAll(@RequestParam("pageNumber")Integer pageNumber, @RequestParam("pageSize")Integer pageSize) {
		logger.info("received a getAll request");
		if(null == pageNumber && null == pageSize) {
			return getAll();
		}if(null == pageNumber || null == pageSize) {
			throw exceptionFactory.generateCustomStatus(HttpStatus.BAD_REQUEST, getAllPageableParamValidation);
		}
		try {
			Page<Customer> result = customerService.getAll(pageNumber, pageSize);
			return convertGetAllToHATEOASResponse(result.getContent(),result.getTotalPages(), pageNumber, pageSize);
		} catch (Exception e) {
			throw exceptionFactory.generateServerInternalErrorResponse(e);
		}
		
	}
	
	/**
	 * 
	 * @return All the customers not paged
	 */
	@RequestMapping(path = {  "/" }, method = RequestMethod.GET)
	public CustomerList getAll() {
		try {
			logger.info("received a getAll request");
			List<Customer> all = customerService.getAll();
			logger.debug("retrived all customers from service", all);
			CustomerList resourceList = convertToHATEOASResponse(all);
			logger.debug("converted to HATEOAS response");
			return resourceList;
		}  catch (Exception e) {
			throw exceptionFactory.generateServerInternalErrorResponse(e);
		}
	}
	

	/**
	 * 
	 * @return All the customers not paged
	 */
	@RequestMapping(path = {  "/test" }, method = RequestMethod.GET)
	public CustomerList test() {
		Customer c = new Customer("test", "test", "test");
		customerRepository.save(c);
		return getAll();
	}

	/**
	 * 
	 * @param id
	 * @return the customer with Id
	 */
	@RequestMapping(path = "/customers/{id}")
	public CustomerResource getCustomer(@PathVariable(name = "id", required = true) String id) {

		try {
			logger.debug("received a getCustomer request for id:%s", id);
			Optional<Customer> customer = customerService.get(id);
			logger.debug("called customerService.get(%s) return %s found", id, (customer.isPresent() ? "1" : "not"));
			if (!customer.isPresent()) {
				logger.debug("return 404");
				throw exceptionFactory.generateNotFoundResponse(Customer.class);
			}

			return convertToHATEOASResponse(customer);
		} catch (Exception e) {
			throw exceptionFactory.generateServerInternalErrorResponse(e);
		}
	}

	/**
	 * Adds the links for the resource as per the HATEOAS specification
	 * @param customer to convert in HATEOAS resource
	 * @return the HATEOAS resource
	 */
	protected CustomerResource convertToHATEOASResponse(Optional<Customer> customer) {
		CustomerResourceAssembler resourceAssembler = new CustomerResourceAssembler();
		return resourceAssembler.toResource(customer.get());
	}

	/**
	 * Adds the links for the resource as per the HATEOAS specification
	 * @param customers the list of customers to convert in HATEOAS resource
	 * @return the HATEOAS resource
	 */
	protected CustomerList convertToHATEOASResponse(List<Customer> customers) {

		CustomerResourceAssembler customerResourceAssembler = new CustomerResourceAssembler();
		List<CustomerResource> resources = customerResourceAssembler.toResources(customers);
		CustomerList resourceList = new CustomerList(resources);
		resourceList.add(customerResourceAssembler.getGetAllLink());
		resourceList.add(customerResourceAssembler.getGetAllPageableLink());
		return resourceList;
	}
	
	protected CustomerList convertGetAllToHATEOASResponse(List<Customer> content, int totalPages, Integer currentPageNumber,
			Integer pageSize) {
		CustomerList resourceList = convertToHATEOASResponse(content);
//		ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getCustomer(customer.getCustomerId())).withSelfRel();
		if(currentPageNumber > 0) {
			resourceList.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getAll(currentPageNumber-1, pageSize)).withRel("Previous"));
		}
		if(currentPageNumber+1 < totalPages) {
			resourceList.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomersController.class).getAll(currentPageNumber+1, pageSize)).withRel("Next"));
		}
		return resourceList;
		
	}



}
