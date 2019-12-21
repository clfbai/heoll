package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import com.boyu.erp.platform.usercenter.TPOS.entity.Worker;
import lombok.Data;

@Data
public class DeliveryOrder {
    /**
     * 单据总行数
     */
    private Integer totalOrderLines;
    /**
     * 出库单号 必填
     */
    private String deliveryOrderCode;
    /**
     * 出库单类型 必填
     */
    private String orderType;
    /**
     * 仓库编码 必填
     */
    private String warehouseCode;
    /**
     * 出库单创建时间 必填
     */
    private String createTime;
    /**
     * 要求出库时间
     */
    private String scheduleDate;
    /**
     * 物流公司编码
     */
    private String logisticsCode;
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 提货方式
     */
    private String transportMode;
    /**
     * 提货人信息
     */
    private Worker pickerInfo;
    /**
     * 发件人信息
     */
    private Worker senderInfo;
    /**
     * 收件人信息
     */
    private Worker receiverInfo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 扩展1
     */
    private String ext1;
    /**
     * 扩展2
     */
    private String ext2;
    /**
     * Y 允许部分发货，N不允许
     */
    private String partialShipment;
    /**
     * 运单号
     */
    private String expressCode;
}
