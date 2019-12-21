package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * loc_bst_pg(货位装箱未决库存 表)
 * @author 
 */
@Data
@NoArgsConstructor
public class LocBstPg implements Serializable {
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 装箱代码
     */
    private String boxCode;

    /**
     * 货位ID
     */
    private Long locId;

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
    private Integer qty;

    /**
     * 预计日期
     */
    private Date estDate;

    private static final long serialVersionUID = 1L;


}