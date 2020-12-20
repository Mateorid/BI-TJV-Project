package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.Assembler.CustomerModelAssembler;
import cz.cvut.fit.gorgomat.dto.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.dto.CustomerModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerModelAssembler customerModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerModelAssembler customerModelAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.customerService = customerService;
        this.customerModelAssembler = customerModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerModel> create(@RequestBody CustomerCreateDTO customer) {
        CustomerModel inserted = customerService.create(customer);
        return ResponseEntity.created(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).byId(inserted.getId()))
                        .toUri()).build();
    }

    @GetMapping(value = "/customer")
    public PagedModel<CustomerModel> getCustomers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @Nullable @RequestParam String name,
                                                  @Nullable @RequestParam String email) {
        Page<Customer> customerPage;
        if (email != null)
            customerPage = customerService.findAllByEmail(email, PageRequest.of(page, size));
        else if (name != null)
            customerPage = customerService.findAllByName(name, PageRequest.of(page, size));
        else
            customerPage = customerService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(customerPage, customerModelAssembler);
    }

    @GetMapping("/customer/{id}")
    public CustomerModel byId(@PathVariable long id) {
        CustomerModel model = customerService.findByIdAsModel(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getCustomers(0, 5, null, null)
        ).withRel(IanaLinkRelations.COLLECTION));
        return model;}

    @PutMapping("/customer/{id}")
    public CustomerModel update(@PathVariable long id, @RequestBody CustomerCreateDTO customer) {
        try {
            return customerService.update(id, customer);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomerModel delete(@PathVariable long id) {
        try {
            return customerService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Error e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
