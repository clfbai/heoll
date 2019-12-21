package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * wareh_bst_pg  (仓库装箱未决库存)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class WarehBstPg  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数量
     */
    private Short qty;

    /**
     * 预计日期
     */
    private Date estDate;
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 装箱代码
     */
    private String boxCode;

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
}