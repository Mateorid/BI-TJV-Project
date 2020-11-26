package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentDTO;
import cz.cvut.fit.gorgomat.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/equipment")
    List<EquipmentDTO> getEquipment(@RequestParam Boolean available, @RequestParam String type, @RequestParam("0") int size) {
        if (available != null)
            return equipmentService.findAllByAvailability(available);
        if (type != null && size != 0)
            return equipmentService.findAllByTypeAndSize(type, size);
        return equipmentService.findAll();
    }

    @GetMapping("/equipment/{id}")
    EquipmentDTO byId(@PathVariable long id) {
        return equipmentService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/equipment")
    EquipmentDTO save(@RequestBody EquipmentCreateDTO equipment) {
        return equipmentService.create(equipment);
    }

    @PutMapping("/equipment/{id}")
    EquipmentDTO update(@PathVariable long id, @RequestBody EquipmentCreateDTO equipment) throws Exception {
        return equipmentService.update(id, equipment);
    }
}