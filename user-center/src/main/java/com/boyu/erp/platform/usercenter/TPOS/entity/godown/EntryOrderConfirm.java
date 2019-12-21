package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 类描述:
 *
 * @Description: 入库单确认EntryOrder类
 * @auther: CLF
 * @date: 2019/11/6 11:42
 */
@Data
@XmlRootElement(name = "entryOrder")
public class EntryOrderConfirm  {

    /**
     * 单据总行数，int，当单据需要分多个请求发送时，发送方需要将totalOrderLines填入，
     * 接收方收到后，根据实际接收到的条数和totalOrderLines进行比对，
     * 如果小于，则继续等待接收请求。如果等于，则表示该单据的所有请求发送完成。
     */
    private Integer totalOrderLines;


    /**
     * 入库单编码, string (50) ,  必填
     */
    private String entryOrderCode;
    /**
     * 货主编码,
     */
    private String ownerCode;
    /**
     * 仓库编码,(仓库代码)，必填
     */
    private String warehouseCode;
    /**
     * 仓储系统入库单ID,string(50) ,条件必填
     */
    private String entryOrderId;
    /**
     * 入库单类型 ，
     * SCRK=生产入库，
     * LYRK=领用入库，
     * CCRK=残次品入库，
     * CGRK=采购入库,
     * DBRK=调拨入库,
     * QTRK=其他入库，
     * B2BRK=B2B入库
     */
    private String entryOrderType;
    /**
     * 外部业务编码,消息ID,用于去重,ISV对于同一请求，分配一个唯一性的编码。
     * 用来保证因为网络等原因导致重复传输，请求不会被重复处理,,必填
     */
    private String outBizCode;
    /**
     * 支持出入库单多次收货,int，多次收货后确认时0表示入库单最终状态确认；
     * 1表示入库单中间状态确认；每次入库传入的数量为增量。
     */
    private String confirmType;
    /**
     * 入库单状态, 必填
     * (NEW-未开始处理,ACCEPT-仓库接单 ,PARTFULFILLED-部分收货完成,
     * FULFILLED-收货完成,EXCEPTION-异常,CANCELED-取消,
     * CLOSED-关闭,REJECT-拒单,CANCELEDFAIL-取消失败),(只传英文编码)
     */
    private String status;

    private String operateTime;

    private String remark;

}
