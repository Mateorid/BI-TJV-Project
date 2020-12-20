package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;
    CustomerCreateDTO cDTO = new CustomerCreateDTO("test", "test");
    private final Pageable pageable = PageRequest.of(0, 3);

    @Test
    void create() {
//        customerController.create(cDTO);
//        Mockito.verify(customerService, Mockito.atLeastOnce()).create(any(CustomerCreateDTO.class));
    }

    @Test
    void getCustomers() {
//        customerController.getCustomers(1,1,null,null);
//        Mockito.verify(customerService, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void byId() {
    }

    @Test
    void update() {
        customerController.update(5L, cDTO);
        Mockito.verify(customerService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(CustomerCreateDTO.class));
    }

    @Test
    void delete() {
        customerController.delete(5L);
        Mockito.verify(customerService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}