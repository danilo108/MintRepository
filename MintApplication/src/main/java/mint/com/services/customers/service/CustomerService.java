package mint.com.services.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import mint.com.services.customers.model.Customer;
/**
 * Service class for Customer
 * @author Danilo
 *
 */
public interface CustomerService {

	/**
	 * 
	 * @return the List of all the customers
	 */
	List<Customer> getAll();
	
	/**
	 * Return a page list of all the customers
	 * @param pageNumber the page number to get
	 * @param pageSize the number of record per page
	 * @return a page list of all the cutomers
	 */
	Page<Customer> getAll(int pageNumber, int pageSize);

	/**
	 * 
	 * @param id The id of the customer
	 * @return the customer with id 
	 */
	Optional<Customer> get(String id);

}