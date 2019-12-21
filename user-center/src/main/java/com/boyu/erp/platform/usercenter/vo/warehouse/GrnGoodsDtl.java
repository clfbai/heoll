package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class GrnGoodsDtl extends StbDtl implements Serializable {


    /**
     * 库存单编号
     */
    private String stbNum;

    private Long unitId;
    /**
     * 商品Id
     */
    private Long prodId;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 助记码(来自商品品种)
     */
    private String inputCode;
    /**
     * 商品名称(来自商品品种)
     */
    private String prodName;
    /**
     * 序号(来自商品品种)
     */
    private String synSn;
    /**
     * 计量单位
     */
    private String uomName;
    /**
     * 规格
     */
    private String specId;
    /**
     * 版型名
     */
    private String editionCp;
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
     * 数量
     */
    private BigDecimal qty;
    /**
     * 预期数量
     */
    private BigDecimal expdQty;
    /**
     * 金额
     */
    private BigDecimal val;
    /**
     * 返利
     */
    private BigDecimal rwd;
    /**
     * 税款
     */
    private BigDecimal tax;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 市场单价
     */
    private BigDecimal mkUnitPrice;


    /**
     * 附加价格
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
    private String dtlRemarks;

    private String specName;
}
