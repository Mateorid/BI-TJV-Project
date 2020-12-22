package cz.cvut.fit.gorgomat.Assembler;

import cz.cvut.fit.gorgomat.controller.MyOrderController;
import cz.cvut.fit.gorgomat.dto.MyOrderModel;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class MyOrderModelAssembler extends RepresentationModelAssemblerSupport<MyOrder, MyOrderModel> {
    public MyOrderModelAssembler() {
        super(MyOrderController.class, MyOrderModel.class);
    }

    @Override
    public MyOrderModel toModel(MyOrder myOrder) {
        Collection<Equipment> equipment = myOrder.getEquipments();
        Customer customer = myOrder.getCustomer();
        return new MyOrderModel(
                myOrder.getId(),
                myOrder.getDateFrom(),
                myOrder.getDateTo(),
                customer == null ? null : customer.getId(),
                equipment == null ? null : equipment.stream().map(Equipment::getId).collect(Collectors.toList())
        );
//        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MyOrderController.class).getMyOrders(0,5,null,null))); //todo :)
//        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MyOrderController.class).byId(myOrder.getId())));
    }
}
