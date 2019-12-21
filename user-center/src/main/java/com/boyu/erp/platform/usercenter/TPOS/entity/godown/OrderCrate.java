package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlRootElement(name = "orderCrate")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "OrderLine",
})
public class OrderCrate {

    private List<OrderLineCreate> OrderLine;
}
