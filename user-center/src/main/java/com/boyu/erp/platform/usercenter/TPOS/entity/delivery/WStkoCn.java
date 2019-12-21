package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * WMS出库单确认
 * @author 
 */
@Data
public class WStkoCn implements Serializable {
    /**
     * 订单Id
     */
    private String id;

    /**
     * 单据总行数
     */
    private String ttlOrdLine;

    /**
     * 出库单编码
     */
    private String stkoCode;

    /**
     * 仓库编码
     */
    private String warehCode;

    /**
     * 仓储系统出库单ID
     */
    private String stkoId;

    /**
     * 单据类型
     */
    private String stkoType;

    /**
     * 出库单状态
     */
    private String stkoStatus;

    /**
     * 多次发货
     */
    private String cnType;

    /**
     * 外部业务编码
     */
    private String obizCode;

    /**
     * 物流公司编码
     */
    private String logCode;

    /**
     * 物流公司名称
     */
    private String logName;

    /**
     * 运单号
     */
    private String exprCode;

    /**
     * 订单完成时间
     */
    private Date cnTime;

    /**
     * 登记时间
     */
    private Date regTime;

    /**
     * 结束时间
     */
    private Date exeTime;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}