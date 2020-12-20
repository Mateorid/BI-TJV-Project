package cz.cvut.fit.gorgomat.Assembler;

import cz.cvut.fit.gorgomat.controller.CustomerController;
import cz.cvut.fit.gorgomat.dto.CustomerModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerModel> {
    public CustomerModelAssembler() {
        super(CustomerController.class, CustomerModel.class);
    }

    @Override
    public CustomerModel toModel(Customer customer) {
        return new CustomerModel(
                customer.getId(),
                customer.getName(),
                customer.getEmail());
        //.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomers(0,5,null,null))); //todo :)
        //.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).byId(customer.getId())));
    }
}
