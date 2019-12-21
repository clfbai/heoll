package com.boyu.erp.platform.usercenter.TPOS.model;

import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.DeliveryOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLines;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "request")
public class DeliveryOrderModel {
    /**
     * 出库单
     */
    private DeliveryOrder deliveryOrder;
    /**
     * 出库单详情
     */
    private OrderLines orderLines;
}
