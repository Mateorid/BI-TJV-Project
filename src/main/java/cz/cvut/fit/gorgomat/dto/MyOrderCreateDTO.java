package cz.cvut.fit.gorgomat.dto;

import java.sql.Date;
import java.util.List;

public class MyOrderCreateDTO {
    private final Date dateFrom;
    private final Date dateTo;

    private final Long customerId;

    private final List<Long> equipmentIds;

    public MyOrderCreateDTO(Date dateFrom, Date dateTo, Long customerId, List<Long> equipmentIds) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.customerId = customerId;
        this.equipmentIds = equipmentIds;
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
