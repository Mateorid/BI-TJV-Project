package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAllByNameContaining(String name, Pageable pageable);

    Page<Customer> findAllByEmailContaining(String email, Pageable pageable);
}
