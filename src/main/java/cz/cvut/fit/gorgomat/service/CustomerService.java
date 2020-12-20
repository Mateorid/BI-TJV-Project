package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.repository.CustomerRepository;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MyOrderRepository myOrderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, MyOrderRepository myOrderRepository) {
        this.customerRepository = customerRepository;
        this.myOrderRepository = myOrderRepository;
    }

    public CustomerModel create(CustomerCreateDTO customerCreateDTO) {
        return toModel(
                customerRepository.save(
                        new Customer(customerCreateDTO.getName(), customerCreateDTO.getEmail())
                )
        );
    }

    @Transactional
    public CustomerModel update(Long id, CustomerCreateDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new NoSuchElementException("No customer with such ID found");
        Customer customer = optionalCustomer.get();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return toModel(customer);
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerModel> findByIdAsModel(Long id) {
        return toModel(customerRepository.findById(id));
    }

    public List<Customer> findByIds(List<Long> ids) {
        return customerRepository.findAllById(ids);
    }

    public Page<Customer> findAllByName(String name, Pageable pageable) {
        return customerRepository.findAllByNameContaining(name, pageable);
    }

    public Page<Customer> findAllByEmail(String email, Pageable pageable) {
        return customerRepository.findAllByEmailContaining(email, pageable);
    }

    public CustomerModel delete(long id) {
        Optional<Customer> customerToDelete = customerRepository.findById(id);
        if (customerToDelete.isEmpty())
            throw new NoSuchElementException("No customer with such ID found");
        if (myOrderRepository.findAllByCustomer_Id(id).size() != 0)
            throw new Error("This customer is part of an order and cant be deleted");
        customerRepository.deleteById(id);
        return toModel(customerToDelete.get());
    }


    private CustomerModel toModel(Customer customer) {
        return new CustomerModel(customer.getId(), customer.getName(), customer.getEmail());
    }

    private Optional<CustomerModel> toModel(Optional<Customer> optionalCustomer) {
        if (optionalCustomer.isEmpty())
            return Optional.empty();
        return Optional.of(toModel(optionalCustomer.get()));
    }
}
