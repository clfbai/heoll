package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StockPolicyModel implements Serializable {
    /**
     * 组织范围
     */
    private String scopeUnit;

    /**
     * 是否保留组织设定
     */
    private String isUnit;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 分类ID
     */
    private String catId;
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

    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;


    private List<StockPolicyModel> list = new ArrayList<>();
}
