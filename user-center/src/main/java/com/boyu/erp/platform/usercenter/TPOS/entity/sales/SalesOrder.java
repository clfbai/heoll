package com.boyu.erp.platform.usercenter.TPOS.entity.sales;

import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLines;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlRootElement(name = "request")
@XmlType(name = "", propOrder = {
        "returnOrder",
        "orderLines"
})
public class SalesOrder {
    private ReturnOrder returnOrder;
    private OrderLines orderLines;
}
