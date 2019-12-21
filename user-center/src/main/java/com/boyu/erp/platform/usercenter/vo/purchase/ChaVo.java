package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ChaVo
 * @Description TODO
 * @Date 2019/6/21 14:58
 * @Created wz
 */
@Data
public class ChaVo implements Serializable {

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
    private String rtPrlRefId;

    /**
     * 结账延迟天数
     */
    private Long stlDlyDays;

    /**
     * 零售价格参照者名称
     */
    private String rtPrlRefName;
}
