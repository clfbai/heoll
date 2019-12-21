package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * unit_prod_cls
 *
 * @author
 */
@Data
public class UnitProdCls implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 销售方式
     */
    private String salesMode;

    /**
     * 推荐等级
     */
    private Integer rcmdLvl;

    /**
     * 零售单价
     */
    private Float rtUnitPrice;

    /**
     * 分销单价
     */
    private Float wsUnitPrice;

    /**
     * 分销税率
     */
    private Float wsTaxRate;

    /**
     * 采购单价
     */
    private Float puUnitPrice;

    /**
     * 采购税率
     */
    private Float puTaxRate;

    /**
     * 供应商ID
     */
    private Integer venderId;

    public UnitProdCls() {
    }

    public UnitProdCls(Long unitId, Long prodClsId) {
        this.unitId = unitId;
        this.prodClsId = prodClsId;
    }
}