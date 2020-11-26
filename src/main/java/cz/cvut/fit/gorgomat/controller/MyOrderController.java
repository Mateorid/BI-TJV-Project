//package cz.cvut.fit.gorgomat.controller;
//
//import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
//import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
//import cz.cvut.fit.gorgomat.service.MyOrderService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//public class MyOrderController {
//
//    private final MyOrderService myOrderService;
//
//    @Autowired
//    public MyOrderController(MyOrderService myOrderService) {
//        this.myOrderService = myOrderService;
//    }
//
//    @GetMapping(name = "/myOrder", params = {"customer_name", "customer_id"})
//    List<MyOrderDTO> getMyOrder(@RequestParam String customer_name, @RequestParam Long customer_id) {
//        if (customer_name == null && customer_id == null)
//            return myOrderService.findAll();
//        else if (customer_name != null)
//            return myOrderService.findAllByCustomerName(customer_name);
//        return myOrderService.findAllByCustomerId(customer_id);
//    }
//
//    @GetMapping("/myOrder/{id}")
//    MyOrderDTO byId(@PathVariable long id) {
//        return myOrderService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
//
//    /*
//        @GetMapping(name = "/myOrder", params = {"customer_name"})
//        List<MyOrderDTO> byCustomerName(@RequestParam String customer_name) {
//            return myOrderService.findAllByCustomerName(customer_name);
//        }
//
//        @GetMapping(name = "/myOrder", params = {"customer_id"})
//        List<MyOrderDTO> byCustomerID(@RequestParam Long customer_id) {
//            return myOrderService.findAllByCustomerId(customer_id);
//        }
//    */
//    @PostMapping("/myOrder")
//    MyOrderDTO save(@RequestBody MyOrderCreateDTO order) throws Exception {
//        return myOrderService.create(order);
//    }
//
//    @PutMapping("/myOrder/{id}")
//    MyOrderDTO update(@PathVariable long id, @RequestBody MyOrderCreateDTO order) throws Exception {
//        return myOrderService.update(id, order);
//    }
//}