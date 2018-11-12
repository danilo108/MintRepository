package mint.com.services.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mint.com.services.customers.model.Customer;
import mint.com.services.customers.model.repositories.CustomerRepository;

@Service()
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerServiceImpl() {
	}
	
	/* (non-Javadoc)
	 * @see mint.com.services.customers.controller.CustomerService#getAll()
	 */
	@Override
	public List<Customer> getAll(){
		return this.customerRepository.findAll();
	}

	@Override
	public Optional<Customer> get(String id) {
		return this.customerRepository.findById(id);
	}

	@Override
	public Page<Customer> getAll(int pageNumber, int pageSize) {
		return this.customerRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}
	
}
