package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.Assembler.MyOrderModelAssembler;
import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderModel;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import cz.cvut.fit.gorgomat.service.MyOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/v1")
public class MyOrderController {

    private final MyOrderService myOrderService;
    private final MyOrderModelAssembler myOrderModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public MyOrderController(MyOrderService myOrderService, MyOrderModelAssembler myOrderModelAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.myOrderService = myOrderService;
        this.myOrderModelAssembler = myOrderModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping("/myOrders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MyOrderModel> create(@RequestBody MyOrderCreateDTO order) {
        try {
            MyOrderModel inserted = myOrderService.create(order);
            return ResponseEntity.created(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).byId(inserted.getId()))
                            .toUri()).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/myOrders")
    public PagedModel<MyOrderModel> getMyOrder(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @Nullable @RequestParam String customerName,
                                               @Nullable @RequestParam Long customerId) {
        Page<MyOrder> orderPage;
        if (customerName != null)
            orderPage = myOrderService.findAllByCustomerName(customerName, PageRequest.of(page, size));
        else if (customerId != null)
            orderPage = myOrderService.findAllByCustomerId(customerId, PageRequest.of(page, size));
        else
            orderPage = myOrderService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(orderPage, myOrderModelAssembler);
    }

    @GetMapping("/myOrders/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MyOrderModel byId(@PathVariable long id) {
        return myOrderService.findByIdAsModel(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/myOrders/{id}")
    public MyOrderModel update(@PathVariable long id, @RequestBody MyOrderCreateDTO order) {
        try {
            return myOrderService.update(id, order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/myOrders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MyOrderModel delete(@PathVariable long id) {
        try {
            return myOrderService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}