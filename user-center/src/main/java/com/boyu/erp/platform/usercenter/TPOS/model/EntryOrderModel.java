package com.boyu.erp.platform.usercenter.TPOS.model;

import com.boyu.erp.platform.usercenter.TPOS.entity.common.ExtendProps;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.EntryOrderCreate;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.OrderCrate;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 推送C-WMS 平台 入库单对象
 */

@Data
@XmlRootElement(name = "request2")
public class EntryOrderModel {
   /**
    * 入库单
    * */
    private EntryOrderCreate entryOrder;

    private OrderCrate orderLines;

    private ExtendProps extendProps;

}
