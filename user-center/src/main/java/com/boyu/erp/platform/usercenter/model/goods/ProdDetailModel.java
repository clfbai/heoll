package com.boyu.erp.platform.usercenter.model.goods;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdDetailModel {
    /**
     * 商品Id
     */
    private Long prodId;
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 商品品种Id
     */
    private Long prodClsId;
    /**
     * 商品名称（cls）
     */
    private String prodName;
    /**
     * 颜色Id
     */
    private Long colorId;
    /**
     * 颜色中文（color）
     */
    private String colorCp;
    /**
     * 助记码（cls）
     */
    private String inputCode;
    /**
     * 计量单位（cls）
     */
    private String uom;
    /**
     * 计量单位中文（code_dtl）
     */
    private String uomCp;
    /**
     * 规格Id
     */
    private Long specId;
    /**
     * 规格名称（Spec）
     */
    private String specCp;
    /**
     * 版型
     */
    private String edition;
    /**
     * 版型别名（code_dtl）
     */
    private String editionCp;
    /**
     * 挂牌价
     */
    private BigDecimal lstPrice;
    /**
     * 序号（prod_cls）
     */
    private Integer seqNum;
}
