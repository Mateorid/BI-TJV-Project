package cz.cvut.fit.gorgomat.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class MyOrder {

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
            joinColumns = @JoinColumn(name = "order_id"), //todo change to myOrder_id
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipment;

    public MyOrder() {
    }

    public MyOrder(Date dateFrom, Date dateTo, Customer customer, List<Equipment> equipment) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyOrder myOrder = (MyOrder) o;
        return id == myOrder.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
