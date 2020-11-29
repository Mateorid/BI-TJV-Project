package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.repository.CustomerRepository;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public CustomerDTO create(CustomerCreateDTO customerCreateDTO) {
        return toDTO(
                customerRepository.save(
                        new Customer(customerCreateDTO.getName(), customerCreateDTO.getEmail())
                )
        );
    }

    @Transactional
    public CustomerDTO update(Long id, CustomerCreateDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new NoSuchElementException("No customer with such ID found");
        Customer customer = optionalCustomer.get();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return toDTO(customer);
    }

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerDTO> findByIdAsDTO(Long id) {
        return toDTO(customerRepository.findById(id));
    }

    public List<Customer> findByIds(List<Long> ids) {
        return customerRepository.findAllById(ids);
    }

    public List<CustomerDTO> findAllByName(String name) {
        return customerRepository.findAllByNameContaining(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CustomerDTO> findAllByEmail(String email) {
        return customerRepository.findAllByEmailContaining(email)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO delete(long id) {
        Optional<Customer> customerToDelete = customerRepository.findById(id);
        if (customerToDelete.isEmpty())
            throw new NoSuchElementException("No customer with such ID found");
        if (myOrderRepository.findAllByCustomer_Id(id).size() != 0)
            throw new Error("This customer is part of an order and cant be deleted");
        customerRepository.deleteById(id);
        return toDTO(customerToDelete.get());
    }


    private CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail());
    }

    private Optional<CustomerDTO> toDTO(Optional<Customer> optionalCustomer) {
        if (optionalCustomer.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalCustomer.get()));
    }
}
