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
    MyOrderDTO byId(@PathVariable long id) {
        return myOrderService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/myOrder")
    MyOrderDTO save(@RequestBody MyOrderCreateDTO order) throws Exception {
        return myOrderService.create(order);
    }

    @PutMapping("/myOrder/{id}")
    MyOrderDTO update(@PathVariable long id, @RequestBody MyOrderCreateDTO order) throws Exception {
        return myOrderService.update(id, order);
    }
}