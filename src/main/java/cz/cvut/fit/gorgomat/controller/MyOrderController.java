package cz.cvut.fit.gorgomat.controller;

import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
import cz.cvut.fit.gorgomat.service.MyOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MyOrderController {

    private final MyOrderService myOrderService;

    @Autowired
    public MyOrderController(MyOrderService myOrderService) {
        this.myOrderService = myOrderService;
    }

    @GetMapping("/myOrder")
    List<MyOrderDTO> getMyOrder(@Nullable @RequestParam String customerName, @Nullable @RequestParam Long customerId) {
        if (customerName != null)
            return myOrderService.findAllByCustomerName(customerName);
        if (customerId != null)
            return myOrderService.findAllByCustomerId(customerId);
        return myOrderService.findAll();
    }

    @GetMapping("/myOrder/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    MyOrderDTO byId(@PathVariable long id) {
        return myOrderService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/myOrder")
    MyOrderDTO create(@RequestBody MyOrderCreateDTO order) {
        try {
            return myOrderService.create(order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/myOrder/{id}")
    MyOrderDTO update(@PathVariable long id, @RequestBody MyOrderCreateDTO order) {
        try {
            return myOrderService.update(id, order);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/myOrder/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    MyOrderDTO delete(@PathVariable long id) {
        try {
            return myOrderService.delete(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}