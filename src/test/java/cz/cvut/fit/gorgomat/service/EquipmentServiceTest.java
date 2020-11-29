package cz.cvut.fit.gorgomat.service;

import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentDTO;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.repository.EquipmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class EquipmentServiceTest {

    @Autowired
    private EquipmentService equipmentService;

    @MockBean
    private EquipmentRepository equipmentRepositoryMock;

    @Test
    void create() {
        //Test data
        Equipment testEquipment = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment, "id", 21);
        EquipmentCreateDTO equipmentCreateDTO = new EquipmentCreateDTO(69, "SpeedySticks", true);

        //Mock
        //We want to return the testEquipment for all passed equipments
        BDDMockito.given(equipmentRepositoryMock.save(any(Equipment.class))).willReturn(testEquipment);
        EquipmentDTO returnedEquipmentDTO = equipmentService.create(equipmentCreateDTO);

        //Test
        // Option with equals() in EquipmentDTO
        EquipmentDTO expectedEquipmentDTO = new EquipmentDTO((long) 21, 69, "SpeedySticks", true);
        assertEquals(expectedEquipmentDTO, returnedEquipmentDTO);
        // Option without equals() in EquipmentDTO
        assertEquals(returnedEquipmentDTO.getId(), 21);
        assertEquals(returnedEquipmentDTO.getSize(), 69);
        assertEquals(returnedEquipmentDTO.getType(), "SpeedySticks");
        assertTrue(returnedEquipmentDTO.isAvailable());
        //Checking attributes
        ArgumentCaptor<Equipment> argumentCaptor = ArgumentCaptor.forClass(Equipment.class);
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Equipment equipmentProvidedToSave = argumentCaptor.getValue();
        assertEquals("SpeedySticks", equipmentProvidedToSave.getType());
        assertEquals(69, equipmentProvidedToSave.getSize());
        assertTrue(equipmentProvidedToSave.isAvailable());
    }

    @Test
    void update() {
        //Test data
        Equipment originalEquipment = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(originalEquipment, "id", 21);
        EquipmentDTO updatedEquipment = new EquipmentDTO((long) 21, 70, "NotSpeedySticks", false);
        EquipmentCreateDTO updatedCreateDTO = new EquipmentCreateDTO(70, "NotSpeedySticks", false);
        //Mock
        BDDMockito.given(equipmentRepositoryMock.findById(any(Long.TYPE))).willReturn(java.util.Optional.of(originalEquipment));
        //Test
        EquipmentDTO returnedEquipment = equipmentService.update((long) 21, updatedCreateDTO);
        assertEquals(updatedEquipment, returnedEquipment);
        assertEquals(returnedEquipment.getId(), 21);
        assertEquals(returnedEquipment.getSize(), 70);
        assertEquals(returnedEquipment.getType(), "NotSpeedySticks");
        assertFalse(returnedEquipment.isAvailable());
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).findById(any(Long.TYPE));
    }

    @Test
    void findByIds() {
        //Test data
        Equipment testEquipment1 = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment1, "id", (long) 90);
        Equipment testEquipment2 = new Equipment(70, "SlowSticks", true);
        ReflectionTestUtils.setField(testEquipment2, "id", (long) 91);
        List<Equipment> equipmentList = List.of(testEquipment1, testEquipment2);
        List<Long> equipmentIds = List.of((long) 69, (long) 70);
        //mock
        BDDMockito.given(equipmentRepositoryMock.findAllById(equipmentIds)).willReturn(equipmentList);
        //tests
        assertEquals(equipmentList, equipmentService.findByIds(equipmentIds));
        List<Equipment> equipmentIdsTest = equipmentService.findByIds(equipmentIds);
        assertEquals(equipmentIdsTest, equipmentList);
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).findAllById(equipmentIds);
    }

    @Test
    void findAllByAvailability() {
    }

    @Test
    void findAllByTypeAndSize() {
    }

    @Test
    void delete() {
        //Test data
        Equipment testEquipment = new Equipment(69, "SpeedySticks", true);
        ReflectionTestUtils.setField(testEquipment, "id", 21);
        //Mock
        BDDMockito.given(equipmentRepositoryMock.findById(any(Long.TYPE))).willReturn(Optional.of(testEquipment));
        //Test
        equipmentService.delete(testEquipment.getId());
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).findById(any(Long.TYPE));
        Mockito.verify(equipmentRepositoryMock, Mockito.atLeastOnce()).deleteById(any(Long.TYPE));
    }
}