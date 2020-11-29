package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerDTO;
import cz.cvut.fit.gorgomat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/customer")
    List<CustomerDTO> getCustomers(@Nullable @RequestParam String name, @Nullable @RequestParam String email) {
        if (email != null)
            return customerService.findAllByEmail(email);
        if (name != null)
            return customerService.findAllByName(name);
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    CustomerDTO byId(@PathVariable long id) {
        return customerService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/customer")
    CustomerDTO create(@RequestBody CustomerCreateDTO customer) {
        return customerService.create(customer);
    }

    @PutMapping("/customer/{id}")
    CustomerDTO update(@PathVariable long id, @RequestBody CustomerCreateDTO customer) {
        try {
            return customerService.update(id, customer);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/customer/{id}")
    CustomerDTO delete(@PathVariable long id) {
        try {
            return customerService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Error e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
