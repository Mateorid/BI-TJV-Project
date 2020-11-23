package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
