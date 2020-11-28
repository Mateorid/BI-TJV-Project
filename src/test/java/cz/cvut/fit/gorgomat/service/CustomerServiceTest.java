package cz.cvut.fit.gorgomat.service;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepositoryMock;

    @Test
    void create() {
        Customer testCustomer = new Customer("Keanu Reeves", "urawesome@smile.com");
        //Setting id
        ReflectionTestUtils.setField(testCustomer, "id", 21);
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO("Keanu Reeves", "urawesome@smile.com");

        //We want to return the testCustomer for all passed customers
        BDDMockito.given(customerRepositoryMock.save(any(Customer.class))).willReturn(testCustomer);
        CustomerDTO returnedCustomerDTO = customerService.create(customerCreateDTO);

        // Option with equals() in CustomerDTO
        CustomerDTO expectedCustomerDTO = new CustomerDTO((long) 21, "Keanu Reeves", "urawesome@smile.com");
        assertEquals(expectedCustomerDTO, returnedCustomerDTO);

        // Option without equals() in CustomerDTO
        assertEquals(returnedCustomerDTO.getId(), 21);
        assertEquals(returnedCustomerDTO.getName(), "Keanu Reeves");
        assertEquals(returnedCustomerDTO.getEmail(), "urawesome@smile.com");

        //Checking attributes
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Customer customerProvidedToSave = argumentCaptor.getValue();
        assertEquals("Keanu Reeves", customerProvidedToSave.getName());
        assertEquals("urawesome@smile.com", customerProvidedToSave.getEmail());
    }

    @Test
    void findById() {
        //Test data
        Customer test = new Customer("Tim", "tim@mit.com");
        ReflectionTestUtils.setField(test, "id", (long) 21);
        //mock
        BDDMockito.given(customerRepositoryMock.findById((long) 21)).willReturn(Optional.of(test));
        //test
        assertEquals(Optional.of(test), customerService.findById(test.getId()));
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(test.getId());
    }
}