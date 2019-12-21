package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * stb_dtl (库存单明细 表)
 *
 * @author
 */
@Data
@ToString
public class StbDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 排号
     */
    private Long rowNum;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 折率
     */
    private BigDecimal discRate;

    /**
     * 折后价
     */
    private BigDecimal fnlPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 预期数量
     */
    private BigDecimal expdQty;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 金额
     */
    private BigDecimal val;

    /**
     * 返利×
     */
    private BigDecimal rwd;

    /**
     * 税款
     */
    private BigDecimal tax;

    /**
     * 市场单价
     */
    private BigDecimal mkUnitPrice;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 附加价格×
     */
    private BigDecimal appPrice;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 商品品种Id
     */
    private Long prodClsId;

    /**
     * 商品是否启用库存管理
     */
    private String stkAdopted;


    private String prodCode;


    public StbDtl() {
    }

    public StbDtl(Long unitId, String stbNum) {
        this.unitId = unitId;
        this.stbNum = stbNum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (!(o instanceof StbDtl)) {
            return false;
        }
        StbDtl model = (StbDtl) o;
        return Objects.equals(stbNum, model.getStbNum()) &&
                Objects.equals(prodId, model.getProdId()) &&
                Objects.equals(unitId, model.getUnitId());

    }
    @Override
    public int hashCode() {
        return Objects.hash(prodId, stbNum, unitId);
    }
}