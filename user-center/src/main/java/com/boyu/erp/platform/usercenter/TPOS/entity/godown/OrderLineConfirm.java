package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.OrderCommon;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "orderLine")
public class OrderLineConfirm extends OrderCommon {

    /**
     * 实收数量,int，必填
     */
    private String actualQty;

    /**
     * 批次
     */
    private List<Batch> batchs;
    /**
     * 备注,
     */
    private String remark;


}
