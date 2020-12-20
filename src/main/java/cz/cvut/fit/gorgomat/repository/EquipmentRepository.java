package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Page<Equipment> findAllByAvailable(Boolean available, Pageable pageable);

    Page<Equipment> findAllByTypeAndSize(String type, int size, Pageable pageable);
}
