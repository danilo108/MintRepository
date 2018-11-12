package mint.com.services.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import mint.com.services.customers.model.Customer;
import mint.com.services.customers.model.repositories.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private CustomerRepository customerRepository;

	private MockMvc mockMvc;

	@Rule
	public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(this.jUnitRestDocumentation)).build();
	}

	@Test
	public void makeDocumentationForGetAll() throws Exception {
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/")).andExpect(status().isOk())
				.andDo(document("customers/getAll"));
	}

	@Test
	public void getAllTest() throws Exception {
		customerRepository.deleteAll();

		callGetAll().andExpect(jsonPath("$.list", Matchers.empty())).andExpect(jsonPath("$._links",Matchers.not(Matchers.empty())));
		Customer customer1 = createCustomer(1);
		customerRepository.save(customer1);
		assertThat(customer1.getCustomerId() != null);
		callGetAll().andExpect(jsonPath("$.list", Matchers.not(Matchers.empty())));		
		
		
	}

	public ResultActions callGetAll() throws Exception {
		return this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	public Customer createCustomer(int n) {
		Customer customer = new Customer();
		customer.setAddress(n + ", Somewhere street ");
		customer.setName("Name" + n);
		customer.setLastName("Suraname" + n);
		return customer;
	}

	
}
