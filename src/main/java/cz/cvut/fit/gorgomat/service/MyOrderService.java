package cz.cvut.fit.gorgomat.service;


import cz.cvut.fit.gorgomat.dto.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.dto.MyOrderDTO;
import cz.cvut.fit.gorgomat.entity.Customer;
import cz.cvut.fit.gorgomat.entity.Equipment;
import cz.cvut.fit.gorgomat.entity.MyOrder;
import cz.cvut.fit.gorgomat.repository.MyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyOrderService {

    private final MyOrderRepository myOrderRepository;
    private final EquipmentService equipmentService;
    private final CustomerService customerService;

    @Autowired
    public MyOrderService(MyOrderRepository myOrderRepository, EquipmentService equipmentService, CustomerService customerService) {
        this.myOrderRepository = myOrderRepository;
        this.equipmentService = equipmentService;
        this.customerService = customerService;
    }

    public MyOrderDTO create(MyOrderCreateDTO myOrderCreateDTO) {
        List<Equipment> equipments = equipmentService.findByIds(myOrderCreateDTO.getEquipmentIds());
        if (equipments.size() != myOrderCreateDTO.getEquipmentIds().size())
            throw new Error("Some equipment/s missing!"); //todo ask how to make this better

        Customer customer = myOrderCreateDTO.getCustomerId() == null ? null :
                customerService.findById(myOrderCreateDTO.getCustomerId())
                        .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        return toDTO(
                myOrderRepository.save(
                        new MyOrder(myOrderCreateDTO.getDateFrom(), myOrderCreateDTO.getDateTo(), customer, equipments)
                )
        );
    }

    @Transactional
    public MyOrderDTO update(Long id, MyOrderCreateDTO myOrderCreateDTO) {
        Optional<MyOrder> optionalOrder = myOrderRepository.findById(id);
        if (optionalOrder.isEmpty())
            throw new NoSuchElementException("No order with such ID found");
        MyOrder myOrder = optionalOrder.get();

        List<Equipment> equipments = equipmentService.findByIds(myOrderCreateDTO.getEquipmentIds());
        if (equipments.size() != myOrderCreateDTO.getEquipmentIds().size())
            throw new Error("Some equipment/s missing!"); //todo ask how to make this better

        Customer customer = myOrderCreateDTO.getCustomerId() == null ? null :
                customerService.findById(myOrderCreateDTO.getCustomerId())
                        .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        myOrder.setCustomer(customer);
        myOrder.setEquipments(equipments);
        myOrder.setDateTo(myOrderCreateDTO.getDateTo());
        myOrder.setDateFrom(myOrderCreateDTO.getDateFrom());
        return toDTO(myOrder);
    }

    public List<MyOrderDTO> findAll() {
        return myOrderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MyOrder> findById(long id) {
        return myOrderRepository.findById(id);
    }

    public Optional<MyOrderDTO> findByIdAsDTO(long id) {
        return toDTO(myOrderRepository.findById(id));
    }

    public List<MyOrder> findByIds(List<Long> ids) {
        return myOrderRepository.findAllById(ids);
    }

    public List<MyOrderDTO> findAllByCustomerId(Long id) {
        return myOrderRepository.findAllByCustomer_Id(id)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MyOrderDTO> findAllByCustomerName(String name) {
        return myOrderRepository.findAllByCustomer_NameContaining(name)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MyOrderDTO delete(long id) {
        Optional<MyOrder> myOrderToDelete = myOrderRepository.findById(id);
        if (myOrderToDelete.isEmpty())
            throw new NoSuchElementException("No equipment with such ID found");
        myOrderRepository.deleteById(id);
        return toDTO(myOrderToDelete.get());
    }

    private MyOrderDTO toDTO(MyOrder myOrder) {
        return new MyOrderDTO(
                myOrder.getId(),
                myOrder.getDateFrom(),
                myOrder.getDateTo(),
                myOrder.getCustomer() == null ? null : myOrder.getCustomer().getId(),
                myOrder.getEquipments().stream().map(Equipment::getId).collect(Collectors.toList())
        );
    }

    private Optional<MyOrderDTO> toDTO(Optional<MyOrder> optionalOrder) {
        if (optionalOrder.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(optionalOrder.get()));
    }
}
