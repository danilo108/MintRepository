package mint.com.services.customers.model.repositories;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mint.com.services.customers.model.Customer;


@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	@Override
	@Cacheable(value="allCustomers")
	public List<Customer> findAll();

	@Override
	@CacheEvict(value="allCustomers", allEntries=true)
	public <S extends Customer> S save(S entity);

	@Override
	@CacheEvict(value="allCustomers", allEntries=true)
	public void delete(Customer entity);
	
	@Override
	@CacheEvict(value="allCustomers", allEntries=true)
	public void deleteAll();
	

	
}
