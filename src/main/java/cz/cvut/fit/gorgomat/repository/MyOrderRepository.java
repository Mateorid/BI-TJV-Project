package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    List<MyOrder> findAllByCustomer_Id(Long id);

    List<MyOrder> findAllByCustomer_Name(String name);

/*
 @Query(
 "SELECT myOrder FROM  MyOrder myOrder" +
 "JOIN MyOrder.equipment.type equipment WHERE lower(myOrder.equipment.type) = :type)"
 )
 Optional<MyOrder> findAllByEquipment(String type);    //todo do a @Query SQL request, would have to rename all Orders xd
 */
}
