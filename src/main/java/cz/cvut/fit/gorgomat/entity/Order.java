package cz.cvut.fit.gorgomat.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private Date dateFrom;

    @NotNull
    private Date dateTo;

    @ManyToOne
    @JoinColumn(name = "customer_order")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_equipment",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipment;

    public Order() {
    }

    public Order(Date dateFrom, Date dateTo, Customer customer, List<Equipment> equipment) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.customer = customer;
        this.equipment = equipment;
    }

    public long getId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Equipment> getEquipments() {
        return equipment;
    }

    public void setEquipments(List<Equipment> equipment) {
        this.equipment = equipment;
    }
}
