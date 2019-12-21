package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * loc_stk_pg  (货位未决库存 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class LocStkPg  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 数量
     */
    private Float qty;
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 商品ID
     */
    private Long prodId;

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

}