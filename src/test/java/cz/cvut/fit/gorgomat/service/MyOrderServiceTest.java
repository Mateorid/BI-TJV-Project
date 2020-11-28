package cz.cvut.fit.gorgomat.service;

import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MyOrderServiceTest {

    @Autowired
    private MyOrderService myOrderService;

    @MockBean
    private MyOrderRepository myOrderRepositoryMock;
///*
    @Test
    void create() throws Exception {
        //Creating test entities for the order test
        //Customer
        Customer testCustomer = new Customer("Keanu Reeves", "urawesome@smile.com");
        ReflectionTestUtils.setField(testCustomer, "id", 15);
        //Equipment list
        Equipment testEquipment1 = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment1, "id", 90);
        Equipment testEquipment2 = new Equipment(70, "SlowSticks", true);
        ReflectionTestUtils.setField(testEquipment2, "id", 91);
        List<Equipment> equipmentList = List.of(testEquipment1, testEquipment2);
        List<Long> equipmentIds = List.of((long) 69, (long) 70);
        //Dates
        Date from = new Date(1950, 10, 11);
        Date to = new Date(1950, 10, 20);

        //Creating test order
        MyOrder testMyOrder = new MyOrder(from, to, testCustomer, equipmentList);
        //Setting order id
        ReflectionTestUtils.setField(testMyOrder, "id", 21);
        MyOrderCreateDTO myOrderCreateDTO = new MyOrderCreateDTO(from, to, (long) 15, equipmentIds);

        //We want to return the testMyOrder for all passed myOrders
        BDDMockito.given(myOrderRepositoryMock.save(any(MyOrder.class))).willReturn(testMyOrder);
        MyOrderDTO returnedMyOrderDTO = myOrderService.create(myOrderCreateDTO);

        // Option with equals() in MyOrderDTO
        MyOrderDTO expectedMyOrderDTO = new MyOrderDTO((long) 21, from, to, (long) 15, equipmentIds);
        assertEquals(expectedMyOrderDTO, returnedMyOrderDTO);

        // Option without equals() in MyOrderDTO
        assertEquals(returnedMyOrderDTO.getId(), 21);
        assertEquals(returnedMyOrderDTO.getDateFrom(), from);
        assertEquals(returnedMyOrderDTO.getDateTo(), to);
        assertEquals(returnedMyOrderDTO.getCustomerId(), 15);
        assertEquals(returnedMyOrderDTO.getEquipmentIds(), equipmentIds);

        //Checking attributes
        ArgumentCaptor<MyOrder> argumentCaptor = ArgumentCaptor.forClass(MyOrder.class);
        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        MyOrder myOrderProvidedToSave = argumentCaptor.getValue();
        assertEquals(from, myOrderProvidedToSave.getDateFrom());
        assertEquals(to, myOrderProvidedToSave.getDateTo());
        assertEquals(testCustomer, myOrderProvidedToSave.getCustomer());
        assertEquals(equipmentList, myOrderProvidedToSave.getEquipments());
    }
// */
}