package cz.cvut.fit.gorgomat.service;

import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import cz.cvut.fit.gorgomat.repository.CustomerRepository;
import cz.cvut.fit.gorgomat.repository.EquipmentRepository;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
class MyOrderServiceTest {

    @Autowired
    private MyOrderService myOrderService;

    @MockBean
    private MyOrderRepository myOrderRepositoryMock;
    @MockBean
    private EquipmentRepository equipmentRepositoryMock;
    @MockBean
    private CustomerRepository customerRepositoryMock;

    @Test
    void create() {
        //Test data
        //Customer
        Customer testCustomer = new Customer("Keanu Reeves", "urawesome@smile.com");
        ReflectionTestUtils.setField(testCustomer, "id", 15);
        //Equipment
        Equipment testEquipment1 = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment1, "id", 90);
        Equipment testEquipment2 = new Equipment(70, "SlowSticks", true);
        ReflectionTestUtils.setField(testEquipment2, "id", 91);
        List<Equipment> equipmentList = List.of(testEquipment1, testEquipment2);
        List<Long> equipmentIds = List.of(90L, 91L);
        //Dates
        Date from = new Date(60935500800000L);
        Date to = new Date(60935500900000L);
        //Test order
        MyOrder testMyOrder = new MyOrder(from, to, testCustomer, equipmentList);
        ReflectionTestUtils.setField(testMyOrder, "id", 21);
        MyOrderCreateDTO myOrderCreateDTO = new MyOrderCreateDTO(from, to, 15L, equipmentIds);
        MyOrderDTO expectedMyOrderDTO = new MyOrderDTO(21L, from, to, 15L, equipmentIds);

        //Mock
        BDDMockito.given(myOrderRepositoryMock.save(any(MyOrder.class))).willReturn(testMyOrder);
        BDDMockito.given(equipmentRepositoryMock.findAllById(any())).willReturn(equipmentList);
        BDDMockito.given(customerRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(testCustomer));
        MyOrderDTO returnedMyOrderDTO = myOrderService.create(myOrderCreateDTO);

        //Test
        assertEquals(expectedMyOrderDTO, returnedMyOrderDTO);
        assertEquals(returnedMyOrderDTO.getId(), 21);
        assertEquals(returnedMyOrderDTO.getDateFrom(), from);
        assertEquals(returnedMyOrderDTO.getDateTo(), to);
        assertEquals(returnedMyOrderDTO.getCustomerId(), 15);
        assertEquals(returnedMyOrderDTO.getEquipmentIds(), equipmentIds);
        ArgumentCaptor<MyOrder> argumentCaptor = ArgumentCaptor.forClass(MyOrder.class);
        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).findAllById(any());
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(any());
        MyOrder myOrderProvidedToSave = argumentCaptor.getValue();
        assertEquals(from, myOrderProvidedToSave.getDateFrom());
        assertEquals(to, myOrderProvidedToSave.getDateTo());
        assertEquals(testCustomer, myOrderProvidedToSave.getCustomer());
        assertEquals(equipmentList, myOrderProvidedToSave.getEquipments());
    }

    @Test
    void update() {
        //Test data
        //Customers
        Customer originalCustomer = new Customer("Keanu Reeves", "urawesome@smile.com");
        ReflectionTestUtils.setField(originalCustomer, "id", 15L);
        Customer updatedCustomer = new Customer("The Rock", "poop@hard.com");
        ReflectionTestUtils.setField(originalCustomer, "id", 16L);
        //Equipments
        Equipment testEquipment1 = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment1, "id", 90L);
        Equipment testEquipment2 = new Equipment(70, "SlowSticks", true);
        ReflectionTestUtils.setField(testEquipment2, "id", 91L);
        List<Equipment> equipmentList = List.of(testEquipment1, testEquipment2);
        List<Equipment> updatedEquipmentList = List.of(testEquipment2);
        List<Long> updatedEquipmentIds = List.of(91L);
        //Dates
        Date from = new Date(60935500800000L);
        Date to = new Date(60935500900000L);
        //Orders
        MyOrder originalOrder = new MyOrder(from, to, originalCustomer, equipmentList);
        ReflectionTestUtils.setField(originalOrder, "id", 21L);
        MyOrderCreateDTO updatedOrderCDTO = new MyOrderCreateDTO(to, from, 16L, updatedEquipmentIds);
        MyOrderDTO expectedMyOrderDTO = new MyOrderDTO(21L, to, from, 16L, updatedEquipmentIds);

        //Mock
        BDDMockito.given(myOrderRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(originalOrder));
        BDDMockito.given(equipmentRepositoryMock.findAllById(any())).willReturn(updatedEquipmentList);
        BDDMockito.given(customerRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(originalCustomer));
        MyOrderDTO returnedMyOrderDTO = myOrderService.update(21L, updatedOrderCDTO);

        //Test
        assertEquals(expectedMyOrderDTO, returnedMyOrderDTO);
        assertEquals(returnedMyOrderDTO.getId(), expectedMyOrderDTO.getId());
        assertEquals(returnedMyOrderDTO.getDateFrom(), expectedMyOrderDTO.getDateFrom());
        assertEquals(returnedMyOrderDTO.getDateTo(), expectedMyOrderDTO.getDateTo());
        assertEquals(returnedMyOrderDTO.getCustomerId(), expectedMyOrderDTO.getCustomerId());
        assertEquals(returnedMyOrderDTO.getEquipmentIds(), expectedMyOrderDTO.getEquipmentIds());
        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).findById(any());
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).findAllById(any());
        Mockito.verify(customerRepositoryMock, Mockito.atLeastOnce()).findById(any());
    }

    @Test
    void delete() {
        //Test data
        MyOrder order = mock(MyOrder.class);
        //Mock
        BDDMockito.given(myOrderRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(order));
        //Test
        myOrderService.delete(order.getId());
        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).findById(any(Long.TYPE));
        Mockito.verify(myOrderRepositoryMock, Mockito.atLeastOnce()).deleteById(any(Long.TYPE));
    }
}