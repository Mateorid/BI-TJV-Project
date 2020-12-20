package cz.cvut.fit.gorgomat.dto;

import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class MyOrderModel extends RepresentationModel<MyOrderModel> {
    private final Long id;
    private final Date dateFrom;
    private final Date dateTo;

    private final Long customerId;

    private final List<Long> equipmentIds;

    public MyOrderModel(Long id, Date dateFrom, Date dateTo, Long customerId, List<Long> equipmentIds) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.customerId = customerId;
        this.equipmentIds = equipmentIds;
    }

    public Long getId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<Long> getEquipmentIds() {
        return equipmentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyOrderModel that = (MyOrderModel) o;
        return id.equals(that.id) &&
                dateFrom.equals(that.dateFrom) &&
                dateTo.equals(that.dateTo) &&
                customerId.equals(that.customerId) &&
                equipmentIds.equals(that.equipmentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateFrom, dateTo, customerId, equipmentIds);
    }
}
