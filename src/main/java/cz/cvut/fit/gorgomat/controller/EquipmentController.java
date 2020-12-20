package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.Assembler.EquipmentModelAssembler;
import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentModel;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/v1")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentModelAssembler equipmentModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public EquipmentController(EquipmentService equipmentService, EquipmentModelAssembler equipmentModelAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.equipmentService = equipmentService;
        this.equipmentModelAssembler = equipmentModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping("/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EquipmentModel> create(@RequestBody EquipmentCreateDTO equipment) {
        EquipmentModel inserted = equipmentService.create(equipment);
        return ResponseEntity.created(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).byId(inserted.getId()))
                        .toUri()).build();
    }

    @GetMapping(value = "/equipment")
    public PagedModel<EquipmentModel> getEquipment(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @Nullable @RequestParam Boolean available,
                                                   @Nullable @RequestParam String type,
                                                   @Nullable @RequestParam Integer equipmentSize) {
        Page<Equipment> equipmentPage;
        if (available != null)
            equipmentPage = equipmentService.findAllByAvailability(available, PageRequest.of(page, size));
        else if (type != null && equipmentSize != null)
            equipmentPage = equipmentService.findAllByTypeAndSize(type, equipmentSize, PageRequest.of(page, size));
        else
            equipmentPage = equipmentService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(equipmentPage, equipmentModelAssembler);
    }

    @GetMapping("/equipment/{id}")
    public EquipmentModel byId(@PathVariable long id) {
        EquipmentModel model = equipmentModelAssembler.toModel(equipmentService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getEquipment(0, 5, null, null, null)
        ).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

    @PutMapping("/equipment/{id}")
    public EquipmentModel update(@PathVariable long id, @RequestBody EquipmentCreateDTO equipment) {
        try {
            EquipmentModel model = equipmentService.update(id, equipment);
            model.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(this.getClass()).byId(model.getId())
            ).withSelfRel());
            model.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(this.getClass()).getEquipment(0, 5, null, null, null)
            ).withRel(IanaLinkRelations.COLLECTION));
            return model;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/equipment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EquipmentModel delete(@PathVariable long id) {
        try {
            return equipmentService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Error e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}