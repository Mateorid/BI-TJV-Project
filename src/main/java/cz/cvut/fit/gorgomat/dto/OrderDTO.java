package cz.cvut.fit.gorgomat.dto;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private final Long id;
    private final Date dateFrom;
    private final Date dateTo;

    private final Long customerId;

    private final List<Long> equipmentIds;

    public OrderDTO(Long id, Date dateFrom, Date dateTo, Long customerId, List<Long> equipmentIds) {
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

}
