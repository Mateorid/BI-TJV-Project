package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
