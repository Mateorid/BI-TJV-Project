package cz.cvut.fit.gorgomat.Assembler;

import cz.cvut.fit.gorgomat.controller.EquipmentController;
import cz.cvut.fit.gorgomat.dto.EquipmentModel;
import cz.cvut.fit.gorgomat.entity.Equipment;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EquipmentModelAssembler extends RepresentationModelAssemblerSupport<Equipment, EquipmentModel> {
    public EquipmentModelAssembler() {
        super(EquipmentController.class, EquipmentModel.class);
    }

    @Override
    public EquipmentModel toModel(Equipment equipment) {
        return new EquipmentModel(
                equipment.getId(),
                equipment.getSize(),
                equipment.getType(),
                equipment.isAvailable())            //todo proste nejede :)
                ;//.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquipmentController.class).byId(equipment.getId())));
    }
}
