package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByNameContaining(String name);

    List<Customer> findAllByEmailContaining(String email);
}
