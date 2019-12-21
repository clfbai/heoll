package com.boyu.erp.platform.usercenter.TPOS.common.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "entryOrder")
public class EntryOrder {
    /**
     * 单据总行数，int，当单据需要分多个请求发送时，发送方需要将totalOrderLines填入，接收方收到后，
     * 根据实际接收到的条数和totalOrderLines进行比对，如果小于，则继续等待接收请求。如果等于，则表示该单据的所有请求发送完成。
     */
    private String totalOrderLines;
    /**
     * 入库单编码  ERP 生成传上去回执  not null
     */
    private String entryOrderCode;
    /**
     * 单据属主代码  not null
     */
    private String ownerCode;
    /**
     * 单据仓库代码  not null
     */
    private String warehouseCode;
    /**
     * 仓储系统入库单
     */
    private String entryOrderId;
    /**
     * 入库单类型   not null
     */
    private String entryOrderType;
    /**
     * 外部业务编码, 消息ID, 用于去重, ISV对于同一请求，分配一个唯一性的编码。
     * 用来保证因为网络等原因导致重复传输，请求不会被重复处理, 必填
     */
    private String outBizCode;
    /**
     * 支持出入库单多次收货,
     * 多次收货后确认时
     * 0 表示入库单最终状态确认；
     * 1 表示入库单中间状态确认；
     * 每次入库传入的数量为增量。
     */
    private Integer confirmType;
    /**
     * 入库单状态, string (50) ,  必填(NEW-未开始处理,  ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成,
     * FULFILLED-收货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)
     */
    private String status;
    /**
     * 操作时间,  string (19)
     */
    private String operateTime;
    /**
     * 备注
     */
    private String remark;
}

