package com.boyu.erp.platform.usercenter.TPOS.model;

import lombok.Data;

import java.io.Serializable;

/**
 * WMS添加出库单回执
 * @author HHe
 * @date 2019/11/5 11:35
 */
@Data
public class DeliveryOrderReceiptModel implements Serializable {
    /**
     * 成功失败信息 success|failure
     */
    private String flag;
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 出库单仓储系统编码
     */
    private String deliveryOrderId;
    /**
     * 订单创建时间
     */
    private String createTime;
}
