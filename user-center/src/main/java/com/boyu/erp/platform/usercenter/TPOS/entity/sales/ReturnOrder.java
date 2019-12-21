package com.boyu.erp.platform.usercenter.TPOS.entity.sales;

import com.boyu.erp.platform.usercenter.TPOS.entity.common.SenderAndReceiverInfo;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "returnOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReturnOrder {
    /**
     * ERP的退货入库单编码, string (50) , 必填
     */
    private String returnOrderCode;
    /*仓库编码，必填 */
    private String warehouseCode;
    /*单据类型THRK=退货入库HHRK=换货入库(只传英文编码)*/
    private String orderType;
    private String orderFlag;
    /*原出库单号（ERP分配）,必填*/
    private String preDeliveryOrderCode;
    /*原出库单号（C-WMS分配），条件必填*/
    private String preDeliveryOrderId = preDeliveryOrderCode;
    private String logisticsCode;
    private String logisticsName;
    private String expressCode;
    private String returnReason;
    private String buyerNick;
    /**
     * 发件人信息
     */
    private SenderAndReceiverInfo senderInfo;
    private String remark;
}
