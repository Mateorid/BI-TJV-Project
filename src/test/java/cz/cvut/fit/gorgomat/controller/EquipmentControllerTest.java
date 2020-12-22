package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentModel;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.service.EquipmentService;
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
class EquipmentControllerTest {

    @Autowired
    private EquipmentController equipmentController;

    @MockBean
    private EquipmentService equipmentService;

    //test data
    private final EquipmentCreateDTO dto = new EquipmentCreateDTO(1, "l", true);
    private final EquipmentModel model = new EquipmentModel(1L, 1, "l", true);
    private final Equipment equipment = new Equipment(1, "l", true);


    @Test
    void create() {
        //Mock
        BDDMockito.given(equipmentService.create(dto)).willReturn(model);
        //Tests
        equipmentController.create(dto);
        Mockito.verify(equipmentService, Mockito.atLeastOnce()).create(any(EquipmentCreateDTO.class));
    }

    @Test
    void getEquipment() {
        //Data preparation
        List<Equipment> list = new ArrayList<>();
        list.add(equipment);
        Page<Equipment> page = new PageImpl<>(list);
        //Mock
        BDDMockito.given(equipmentService.findAllByAvailability(any(Boolean.TYPE), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(equipmentService.findAllByTypeAndSize(any(String.class), any(Integer.TYPE), any(Pageable.class)))
                .willReturn(page);
        BDDMockito.given(equipmentService.findAll(any(Pageable.class))).willReturn(page);

        //Tests - all 3 types
        equipmentController.getEquipment(0, 1, true, null, null);
        equipmentController.getEquipment(0, 1, null, "t", 5);
        equipmentController.getEquipment(0, 1, null, null, null);
        Mockito.verify(equipmentService, Mockito.atLeastOnce())
                .findAllByAvailability(any(Boolean.TYPE), any(Pageable.class));
        Mockito.verify(equipmentService, Mockito.atLeastOnce())
                .findAllByTypeAndSize(any(String.class), any(Integer.TYPE), any(Pageable.class));
        Mockito.verify(equipmentService, Mockito.atLeastOnce())
                .findAll(any(Pageable.class));
    }

    @Test
    void byId() {
        //Mock
        BDDMockito.given(equipmentService.findById(any(Long.TYPE))).willReturn(Optional.of(equipment));

        //Test
//        Assertions.assertEquals(model, equipmentController.byId(model.getId()));
        equipmentController.byId(model.getId());
        Mockito.verify(equipmentService, Mockito.atLeastOnce()).findById(any(Long.TYPE));
    }

    @Test
    void update() {
        //Mock
        BDDMockito.given(equipmentService.update(5L, dto)).willReturn(model);

        //Test
        equipmentController.update(5L, dto);
        Mockito.verify(equipmentService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(EquipmentCreateDTO.class));
    }

    @Test
    void delete() {
        equipmentController.delete(5L);
        Mockito.verify(equipmentService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}