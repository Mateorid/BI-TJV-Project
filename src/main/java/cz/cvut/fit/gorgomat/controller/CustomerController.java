package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerDTO;
import cz.cvut.fit.gorgomat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/customer")
    List<CustomerDTO> getCustomers(/*@RequestParam String name, @RequestParam String email*/) {
//        if (name == null && email == null)
        return customerService.findAll();
//        else if (name != null)
//            return customerService.findAllByName(name);
//        return customerService.findAllByEmail(email);
    }

    @GetMapping("/customer/{id}")
    CustomerDTO byId(@PathVariable long id) {
        return customerService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value = "/customer", params = {"name"})
    List<CustomerDTO> byName(@RequestParam String name) {
        return customerService.findAllByName(name);
    }

    @GetMapping(value = "/customer", params = {"email"})
    List<CustomerDTO> byEmail(@RequestParam String email) {
        return customerService.findAllByEmail(email);
    }

    @PostMapping("/customer")
    CustomerDTO save(@RequestBody CustomerCreateDTO customer) {
        return customerService.create(customer);
    }

    @PutMapping("/customer/{id}")
    CustomerDTO update(@PathVariable long id, @RequestBody CustomerCreateDTO customer) throws Exception {
        return customerService.update(id, customer);
    }
}