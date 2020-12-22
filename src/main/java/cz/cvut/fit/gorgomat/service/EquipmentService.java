package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.dto.EquipmentModel;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.repository.EquipmentRepository;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public EquipmentModel create(EquipmentCreateDTO equipmentCreateDTO) {
        return toModel(
                equipmentRepository.save(
                        new Equipment(equipmentCreateDTO.getSize(), equipmentCreateDTO.getType(), equipmentCreateDTO.isAvailable())
                )
        );
    }

    @Transactional
    public EquipmentModel update(Long id, EquipmentCreateDTO equipmentCreateDTO) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(id);
        if (optionalEquipment.isEmpty())
            throw new NoSuchElementException("No equipment with such ID found");
        Equipment equipment = optionalEquipment.get();
        equipment.setType(equipmentCreateDTO.getType());
        equipment.setSize(equipmentCreateDTO.getSize());
        equipment.setAvailable(equipmentCreateDTO.isAvailable());
        return toModel(equipment);
    }

    public Page<Equipment> findAll(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Optional<EquipmentModel> findByIdAsModel(Long id) {
        return toModel(equipmentRepository.findById(id));
    }

    public List<Equipment> findByIds(List<Long> ids) {
        return equipmentRepository.findAllById(ids);
    }

    public Page<Equipment> findAllByAvailability(Boolean available, Pageable pageable) {
        return equipmentRepository.findAllByAvailable(available, pageable);
    }

    public Page<Equipment> findAllByTypeAndSize(String type, int size, Pageable pageable) {
        return equipmentRepository.findAllByTypeAndSize(type, size, pageable);
    }

    public EquipmentModel delete(Long id) {
        Optional<Equipment> equipmentToDelete = equipmentRepository.findById(id);
        if (equipmentToDelete.isEmpty())
            throw new NoSuchElementException("No equipment with such ID found");
        if (myOrderRepository.findAllByEquipmentContaining(equipmentToDelete.get()).size() != 0)
            throw new Error("This equipment is part of an order and cant be deleted");
        equipmentRepository.deleteById(id);
        return toModel(equipmentToDelete.get());
    }

    private EquipmentModel toModel(Equipment equipment) {
        return new EquipmentModel(equipment.getId(), equipment.getSize(), equipment.getType(), equipment.isAvailable());
    }

    private Optional<EquipmentModel> toModel(Optional<Equipment> optionalEquipment) {
        if (optionalEquipment.isEmpty())
            return Optional.empty();
        return Optional.of(toModel(optionalEquipment.get()));
    }
}
