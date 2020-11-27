//package cz.cvut.fit.gorgomat.service;
//
//import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
//import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
//import cz.cvut.fit.gorgomat.entity.MyOrder;
//import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest
//class MyOrderServiceTest {
//
//    @Autowired
//    private MyOrderService myOrderService;
//
//    @MockBean
//    private MyOrderRepository myOrderRepositoryMock;
//
//    @Test
//    void create() {
//        MyOrder testMyOrder = new MyOrder("1950-10-05", "1950-10-09",);
//        //Setting id
//        ReflectionTestUtils.setField(testMyOrder, "id", 21);
//        MyOrderCreateDTO myOrderCreateDTO = new MyOrderCreateDTO("1950-10-05", "1950-10-09");
//
//        //We want to return the testMyOrder for all passed myOrders
//        BDDMockito.given(myOrderRepositoryMock.save(any(MyOrder.class))).willReturn(testMyOrder);
//        MyOrderDTO returnedMyOrderDTO = myOrderService.create(myOrderCreateDTO);
//
//        // Option with equals() in MyOrderDTO
//        MyOrderDTO expectedMyOrderDTO = new MyOrderDTO((long) 21, "1950-10-05", "1950-10-09");
//        assertEquals(expectedMyOrderDTO, returnedMyOrderDTO);
//
//        // Option without equals() in MyOrderDTO
//        assertEquals(returnedMyOrderDTO.getId(), 21);
//        assertEquals(returnedMyOrderDTO.getName(), "1950-10-05");
//        assertEquals(returnedMyOrderDTO.getEmail(), "1950-10-09");
//
//        //Checking attributes
//        ArgumentCaptor<MyOrder> argumentCaptor = ArgumentCaptor.forClass(MyOrder.class);
//        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
//        MyOrder myOrderProvidedToSave = argumentCaptor.getValue();
//        assertEquals("1950-10-05", myOrderProvidedToSave.getName());
//        assertEquals("1950-10-09", myOrderProvidedToSave.getEmail());
//    }
//}