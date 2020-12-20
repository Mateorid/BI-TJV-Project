package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    Page<MyOrder> findAllByCustomer_Id(Long id, Pageable pageable);

    List<MyOrder> findAllByCustomer_Id(Long id);

    Page<MyOrder> findAllByCustomer_NameContaining(String name, Pageable pageable);

    Page<MyOrder> findAllByEquipmentContaining(Equipment equipment, Pageable pageable);

    List<MyOrder> findAllByEquipmentContaining(Equipment equipment);
}
