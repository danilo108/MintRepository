package mint.com.services.customers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mint.com.services.customers.model.Customer;
import mint.com.services.customers.model.repositories.CustomerRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void run(String...args) throws Exception {
        logger.info("Database initialisation");
        customerRepository.deleteAll();
        logger.info("	Delete all - executed. " + customerRepository.count() + " customers in the database");
		customerRepository.save(new Customer("Danilo", "Scuderoni", "George street, Parramatta"));
		customerRepository.save(new Customer("Michael", "Taylor", "Hunter street, Parramatta"));
		customerRepository.save(new Customer("Leila", "Jones", "Philip street, Parramatta"));
        logger.info("	Customers added: " + customerRepository.count());
        logger.info("End of database initialisation");
    }
}