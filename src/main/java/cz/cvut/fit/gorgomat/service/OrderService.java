package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.OrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.OrderDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.Order;
import cz.cvut.fit.gorgomat.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final EquipmentService equipmentService;
    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepository, EquipmentService equipmentService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.equipmentService = equipmentService;
        this.customerService = customerService;
    }

    public OrderDTO create(OrderCreateDTO orderCreateDTO) throws Exception {
        List<Equipment> equipments = equipmentService.findByIds(orderCreateDTO.getEquipmentIds());
        if (equipments.size() != orderCreateDTO.getEquipmentIds().size())
            throw new Exception("Some equipment/s missing!"); //todo make this better

        Customer customer = orderCreateDTO.getCustomerId() == null ? null :
                customerService.findById(orderCreateDTO.getCustomerId())
                        .orElseThrow(() -> new Exception("Customer not found")); //todo make this better

        return toDTO(
                orderRepository.save(
                        new Order(orderCreateDTO.getDateFrom(), orderCreateDTO.getDateTo(), customer, equipments)
                )
        );
    }

    @Transactional
    public OrderDTO update(Long id, OrderCreateDTO orderCreateDTO) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty())
            throw new Exception("No order with such id"); //todo make this better
        Order order = optionalOrder.get();

        List<Equipment> equipments = equipmentService.findByIds(orderCreateDTO.getEquipmentIds());
        if (equipments.size() != orderCreateDTO.getEquipmentIds().size())
            throw new Exception("Some equipment/s missing!"); //todo make this better

        Customer customer = orderCreateDTO.getCustomerId() == null ? null :
                customerService.findById(orderCreateDTO.getCustomerId())
                        .orElseThrow(() -> new Exception("Customer not found")); //todo make this better

        order.setCustomer(customer);
        order.setEquipments(equipments);
        order.setDateTo(orderCreateDTO.getDateTo());
        order.setDateFrom(orderCreateDTO.getDateFrom());
        return toDTO(order);
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public Optional<OrderDTO> findByIdAsDTO(long id) {
        return toDTO(orderRepository.findById(id));
    }

    public List<Order> findByIds(List<Long> ids) {
        return orderRepository.findAllById(ids);
    }

    private OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getDateFrom(),
                order.getDateTo(),
                order.getCustomer() == null ? null : order.getCustomer().getId(),
                order.getEquipments().stream().map(Equipment::getId).collect(Collectors.toList())
        );
    }

    private Optional<OrderDTO> toDTO(Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalOrder.get()));
    }
}
