package com.boyu.erp.platform.usercenter.TPOS.model;

import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.Packages;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.DeliveryOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLines;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement(name = "request")
public class DeliveryOrderConfirmModel implements Serializable {

    private DeliveryOrder deliveryOrder;

    private Packages packages;

    private OrderLines orderLines;
}
