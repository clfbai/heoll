package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cha
 * @author 
 */
@Data
public class Cha implements Serializable {

    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;
    /**
     * 授权方式
     */
    private String authMode;

    /**
     * 经营方式
     */
    private String manMode;

    /**
     * 授权区域
     */
    private String authArea;

    /**
     * 加盟费
     */
    private BigDecimal licFee;

    /**
     * 零售价格参照者ID
     */
    private Long rtPrlRefId;

    /**
     * 结账延迟天数
     */
    private Long stlDlyDays;

    private static final long serialVersionUID = 1L;

}