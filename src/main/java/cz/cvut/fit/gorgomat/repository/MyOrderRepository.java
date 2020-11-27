package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    List<MyOrder> findAllByCustomer_Id(Long id);

    List<MyOrder> findAllByCustomer_NameContaining(String name);
}
