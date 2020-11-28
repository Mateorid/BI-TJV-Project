package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentDTO;
import cz.cvut.fit.gorgomat.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping(value = "/equipment")
    List<EquipmentDTO> getEquipment(@Nullable @RequestParam Boolean available, @Nullable @RequestParam String type, @Nullable @RequestParam Integer size) {
        if (available != null)
            return equipmentService.findAllByAvailability(available);
        if (type != null && size != null)
            return equipmentService.findAllByTypeAndSize(type, size);
        return equipmentService.findAll();
    }

    @GetMapping("/equipment/{id}")
    EquipmentDTO byId(@PathVariable long id) {
        return equipmentService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/equipment")
    EquipmentDTO create(@RequestBody EquipmentCreateDTO equipment) {
        return equipmentService.create(equipment);
    }

    @PutMapping("/equipment/{id}")
    EquipmentDTO update(@PathVariable long id, @RequestBody EquipmentCreateDTO equipment) throws Exception {
        try {
            return equipmentService.update(id, equipment);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}