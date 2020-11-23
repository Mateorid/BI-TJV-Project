package cz.cvut.fit.gorgomat.repository;

import cz.cvut.fit.gorgomat.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
