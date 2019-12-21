package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cat_stk_lmt   分类库存预警
 *
 * @author
 */
@Data
@NoArgsConstructor
public class CatStkLmt implements Serializable {

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 分类ID
     */
    private String catId;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 库存上限
     */
    private BigDecimal maxStk;

    /**
     * 库存下限
     */
    private BigDecimal minStk;

    /**
     * 最佳库存
     */
    private BigDecimal bestStk;

    private static final long serialVersionUID = 1L;


}