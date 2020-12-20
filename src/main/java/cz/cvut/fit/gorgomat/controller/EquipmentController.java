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

    @PostMapping("/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    EquipmentDTO create(@RequestBody EquipmentCreateDTO equipment) {
        return equipmentService.create(equipment);
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

    @PutMapping("/equipment/{id}")
    EquipmentDTO update(@PathVariable long id, @RequestBody EquipmentCreateDTO equipment) {
        try {
            return equipmentService.update(id, equipment);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/equipment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    EquipmentDTO delete(@PathVariable long id) {
        try {
            return equipmentService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Error e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}