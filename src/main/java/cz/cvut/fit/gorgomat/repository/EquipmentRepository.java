package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByAvailable(Boolean available);

    List<Equipment> findAllByTypeAndSize(String type, int size);
}
