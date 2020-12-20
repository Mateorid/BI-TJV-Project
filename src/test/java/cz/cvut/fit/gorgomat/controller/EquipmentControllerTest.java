package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.service.EquipmentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EquipmentController equipmentController;

    @MockBean
    private EquipmentService equipmentService;
    EquipmentCreateDTO cDTO = new EquipmentCreateDTO(1, "l", true);

    @Test
    void create() {
//        equipmentController.create(cDTO);
//        Mockito.verify(equipmentService, Mockito.atLeastOnce()).create(any(EquipmentCreateDTO.class));
    }

    @Test
    void getEquipments() {
    }

    @Test
    void byId() {
    }

    @Test
    void update() {
//        equipmentController.update(5L, cDTO);
//        Mockito.verify(equipmentService, Mockito.atLeastOnce()).update(any(Long.TYPE), any(EquipmentCreateDTO.class));
    }

    @Test
    void delete() {
        equipmentController.delete(5L);
        Mockito.verify(equipmentService, Mockito.atLeastOnce()).delete(any(Long.TYPE));
    }
}