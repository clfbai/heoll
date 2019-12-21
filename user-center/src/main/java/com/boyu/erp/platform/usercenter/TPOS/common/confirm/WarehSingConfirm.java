package com.boyu.erp.platform.usercenter.TPOS.common.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 类描述:
 *
 * @Description: 入库单确认类
 * @auther: CLF
 * @date: 2019/11/21 15:31
 */
@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "request")
@XmlType(name = "", propOrder = {
        "entryOrder",
        "orderLines"
})
public class WarehSingConfirm {

    protected EntryOrder entryOrder;

    protected FimOrderLines orderLines;


}


/*  数据结构
   protected JsonRootBean.EntryOrder entryOrder;
   protected JsonRootBean.OrderLines orderLines;

    public class EntryOrder {
       protected String totalOrderLines;
    }

    public class OrderLines {
       protected List<FirmOrderLine> orderLine;
        public class FirmOrderLine {
           protected Batchs batchs;

            public class Batchs {
               protected List<Batch> batch;

                public class Batch {
                   protected String batchCode;
                }

            }
        }
    }
    */



