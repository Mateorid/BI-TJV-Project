package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import cz.cvut.fit.gorgomat.service.MyOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class MyOrderControllerTest {

    @Autowired
    private MyOrderController myOrderController;

    @MockBean
    private MyOrderService myOrderService;

    //test data
    Date from = new Date(60935500800000L);
    Date to = new Date(60935500900000L);
    private final Customer customer = new Customer("test", "test");
    private final MyOrderCreateDTO dto = new MyOrderCreateDTO(from, to, 1L, null);
    private final MyOrderModel model = new MyOrderModel(1L, from, to, 1L, null);
    private final MyOrder order = new MyOrder(from, to, customer, null);


    @Test
    void create() {
        BDDMockito.given(myOrderService.create(dto)).willReturn(model);
        myOrderController.create(dto);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).create(any(MyOrderCreateDTO.class));
    }

    @Test
    void getMyOrders() {
        //Data prep
        List<MyOrder> list = new ArrayList<>();
        list.add(order);
        Page<MyOrder> page = new PageImpl<>(list);
        //Mock
        BDDMockito.given(myOrderService.findAllByCustomerId(any(Long.TYPE), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(myOrderService.findAllByCustomerName(any(String.class), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(myOrderService.findAll(any(Pageable.class))).willReturn(page);
        //Tests
        myOrderController.getMyOrder(0, 1, "null", null);
        myOrderController.getMyOrder(0, 1, null, 1L);
        myOrderController.getMyOrder(0, 1, null, null);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).findAllByCustomerId(any(Long.TYPE), any(Pageable.class));
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).findAllByCustomerName(any(String.class), any(Pageable.class));
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).findAll(any(Pageable.class));
    }

    @Test
    void byId() {
        BDDMockito.given(myOrderService.findById(any(Long.TYPE))).willReturn(Optional.of(order));
        BDDMockito.given(myOrderService.findByIdAsModel(any(Long.TYPE))).willReturn(Optional.of(model));
        myOrderController.byId(1L);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).findById(any(Long.TYPE));
    }

    @Test
    void update() {
        BDDMockito.given(myOrderService.update(5L, dto)).willReturn(model);
        myOrderController.update(5L, dto);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(MyOrderCreateDTO.class));
    }

    @Test
    void delete() {
        myOrderController.delete(5L);
        Mockito.verify(myOrderService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}