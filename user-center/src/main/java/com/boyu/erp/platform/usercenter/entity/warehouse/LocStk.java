package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * loc_stk (货位库存 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class LocStk implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 实际库存
     */
    private Float stkOnHand;

    /**
     * 预期库存
     */
    private Float qtyExpd;

    /**
     * 已配库存
     */
    private Float qtyCmtd;

    /**
     * 配码库存
     */
    private Float qtyAst;

    /**
     * 装箱库存
     */
    private Float qtyBxd;

    /**
     * 包装库存
     */
    private Float qtyPckd;


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
}