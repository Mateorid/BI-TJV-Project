package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentDTO;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public EquipmentDTO create(EquipmentCreateDTO equipmentCreateDTO) {
        return toDTO(
                equipmentRepository.save(
                        new Equipment(equipmentCreateDTO.getSize(), equipmentCreateDTO.getType(), equipmentCreateDTO.isAvailable())
                )
        );
    }

    @Transactional
    public EquipmentDTO update(Long id, EquipmentCreateDTO equipmentCreateDTO) throws Exception {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isEmpty())
            throw new Exception("No equipment with such id"); //todo make this better
        Equipment equipment = optionalEquipment.get();
        equipment.setType(equipmentCreateDTO.getType());
        equipment.setSize(equipmentCreateDTO.getSize());
        equipment.setAvailable(equipmentCreateDTO.isAvailable());
        return toDTO(equipment);
    }

    public List<EquipmentDTO> findAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Optional<EquipmentDTO> findByIdAsDTO(Long id) {
        return toDTO(equipmentRepository.findById(id));
    }

    public List<Equipment> findByIds(List<Long> ids) {
        return equipmentRepository.findAllById(ids);
    }

    public List<EquipmentDTO> findAllByAvailability(Boolean available) {
        return equipmentRepository.findAllByAvailable(available)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EquipmentDTO> findAllByTypeAndSize(String type, int size) {
        return equipmentRepository.findAllByTypeAndSize(type, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EquipmentDTO toDTO(Equipment equipment) {
        return new EquipmentDTO(equipment.getId(), equipment.getSize(), equipment.getType(), equipment.isAvailable());
    }

    private Optional<EquipmentDTO> toDTO(Optional<Equipment> optionalEquipment) {
        if (optionalEquipment.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalEquipment.get()));
    }
}
