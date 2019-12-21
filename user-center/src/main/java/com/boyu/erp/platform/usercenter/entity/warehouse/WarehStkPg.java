package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * wareh_stk_pg (仓库未决库存 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class WarehStkPg implements Serializable {
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 属主ID
     */
    private Long prodId;

    /**
     * 库存类别
     */
    private String stkType;

    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;
    /**
     * 数量
     */
    private Float qty;

    /**
     * 预计日期
     */
    private Date estDate;

    private static final long serialVersionUID = 1L;


}