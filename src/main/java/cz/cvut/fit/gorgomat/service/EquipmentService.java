package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentDTO;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.repository.EquipmentRepository;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final MyOrderRepository myOrderRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, MyOrderRepository myOrderRepository) {
        this.equipmentRepository = equipmentRepository;
        this.myOrderRepository = myOrderRepository;
    }

    public EquipmentDTO create(EquipmentCreateDTO equipmentCreateDTO) {
        return toDTO(
                equipmentRepository.save(
                        new Equipment(equipmentCreateDTO.getSize(), equipmentCreateDTO.getType(), equipmentCreateDTO.isAvailable())
                )
        );
    }

    @Transactional
    public EquipmentDTO update(Long id, EquipmentCreateDTO equipmentCreateDTO) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isEmpty())
            throw new NoSuchElementException("No equipment with such ID found");
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

    public EquipmentDTO delete(Long id) {
        Optional<Equipment> equipmentToDelete = equipmentRepository.findById(id);
        if (equipmentToDelete.isEmpty())
            throw new NoSuchElementException("No equipment with such ID found");
        if (myOrderRepository.findAllByEquipmentContaining(equipmentToDelete.get()).size() != 0)
            throw new Error("This equipment is part of an order and cant be deleted");
        equipmentRepository.deleteById(id);
        return toDTO(equipmentToDelete.get());
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
