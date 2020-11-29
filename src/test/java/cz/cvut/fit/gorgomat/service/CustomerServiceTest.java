package cz.cvut.fit.gorgomat.service;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

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
    void update() {
        //Test data
        Customer originalCustomer = new Customer("Peepo", "peepo@twitch.tv");
        ReflectionTestUtils.setField(originalCustomer, "id", 21);
        CustomerDTO updatedCustomer = new CustomerDTO((long) 21, "Pepe", "pepe@twitch.tv");
        CustomerCreateDTO updatedCreateDTO = new CustomerCreateDTO("Pepe", "pepe@twitch.tv");
        //Mock
        BDDMockito.given(customerRepositoryMock.findById(any(Long.TYPE))).willReturn(java.util.Optional.of(originalCustomer));
        //Test
        CustomerDTO returnedCustomer = customerService.update((long) 21, updatedCreateDTO);
        assertEquals(updatedCustomer, returnedCustomer);
        assertEquals(returnedCustomer.getId(), 21);
        assertEquals(returnedCustomer.getName(), "Pepe");
        assertEquals(returnedCustomer.getEmail(), "pepe@twitch.tv");
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(any(Long.TYPE));
    }

    @Test
    void findAll() {
        customerService.findAll();
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById() {
        //Test data
        Customer test = new Customer("Pepe", "pepe@twitch.tv");
        ReflectionTestUtils.setField(test, "id", (long) 21);
        //mock
        BDDMockito.given(customerRepositoryMock.findById((long) 21)).willReturn(Optional.of(test));
        //tests
        assertEquals(Optional.of(test), customerService.findById(test.getId()));
        assertEquals(test.getId(), 21);
        assertEquals(test.getName(), "Pepe");
        assertEquals(test.getEmail(), "pepe@twitch.tv");
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(test.getId());
    }

    @Test
    void findAllByName() {
        customerService.findAllByName("test");
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findAllByNameContaining(any(String.class));
    }

    @Test
    void findAllByEmail() {
        customerService.findAllByEmail("test");
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findAllByEmailContaining(any(String.class));

    }

    @Test
    void delete() {
        //Test data
        Customer testCustomer = new Customer("Tim", "tim@mit.com");
        ReflectionTestUtils.setField(testCustomer, "id", 21);
        //Mock
        BDDMockito.given(customerRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(testCustomer));
        //Test
        customerService.delete(testCustomer.getId());
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(any(Long.TYPE));
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).deleteById(any(Long.TYPE));
    }
}