package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.service.MyOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class MyOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MyOrderController myOrderController;

    @MockBean
    private MyOrderService myOrderService;
    Date from = new Date(60935500800000L);
    Date to = new Date(60935500900000L);
    MyOrderCreateDTO cDTO = new MyOrderCreateDTO(from, to, 1L, null);

    @Test
    void create() {
//        myOrderController.create(cDTO);
//        Mockito.verify(myOrderService, Mockito.atLeastOnce()).create(any(MyOrderCreateDTO.class));
    }

    @Test
    void getMyOrders() {
    }

    @Test
    void byId() {
    }

    @Test
    void update() {
        myOrderController.update(5L, cDTO);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(MyOrderCreateDTO.class));
    }

    @Test
    void delete() {
        myOrderController.delete(5L);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}