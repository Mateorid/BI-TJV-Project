package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    //test data
    private final CustomerCreateDTO dto = new CustomerCreateDTO("test", "test");
    private final CustomerModel model = new CustomerModel(1L, "test", "test");
    private final Customer customer = new Customer("test", "test");

    @Test
    void create() {
        //Mock
        BDDMockito.given(customerService.create(dto)).willReturn(model);
        //Tests
        customerController.create(dto);
        Mockito.verify(customerService, Mockito.atLeastOnce()).create(any(CustomerCreateDTO.class));
    }

    @Test
    void getCustomers() {
        //Data preparation
        List<Customer> list = new ArrayList<>();
        list.add(customer);
        Page<Customer> page = new PageImpl<>(list);
        //Mock
        BDDMockito.given(customerService.findAllByEmail(any(String.class), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(customerService.findAllByName(any(String.class), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(customerService.findAll(any(Pageable.class))).willReturn(page);

        //Tests
        customerController.getCustomers(0, 1, "test", null);
        customerController.getCustomers(0, 1, null, "test");
        customerController.getCustomers(0, 1, null, null);
        Mockito.verify(customerService, Mockito.atLeastOnce()).findAllByEmail(any(String.class), any(Pageable.class));
        Mockito.verify(customerService, Mockito.atLeastOnce()).findAllByName(any(String.class), any(Pageable.class));
        Mockito.verify(customerService, Mockito.atLeastOnce()).findAll(any(Pageable.class));
    }

    @Test
    void byId() {
        BDDMockito.given(customerService.findById(any(Long.TYPE))).willReturn(Optional.of(customer));

//        Assertions.assertEquals(model, customerController.byId(model.getId()));
        customerController.byId(1L);
        Mockito.verify(customerService, Mockito.atLeastOnce()).findById(any(Long.TYPE));
    }

    @Test
    void update() {
        BDDMockito.given(customerService.update(5L, dto)).willReturn(model);

        customerController.update(5L, dto);
        Mockito.verify(customerService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(CustomerCreateDTO.class));
    }

    @Test
    void delete() {
        customerController.delete(5L);
        Mockito.verify(customerService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}