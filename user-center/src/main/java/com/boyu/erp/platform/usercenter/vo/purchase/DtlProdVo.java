package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname DtlProdVo
 * @Description TODO
 * @Date 2019/7/10 14:24
 * @Created wz
 */
@Data
public class DtlProdVo implements Serializable {

    /**
     * 当前组织/切换组织
     */
    private Long sUnitId;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;
    /**
     * 商品品种代码  非空
     */
    private String prodClsCode;
    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 零售单价
     */
    private Float rtUnitPrice;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 版型   非空
     */
    private String edition;
    /**
     * 颜色ID   非空
     */
    private String color;
    /**
     * 规格ID   非空
     */
    private String spec;
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 分销单价
     */
    private Float wsUnitPrice;
    /**
     * 挂牌价
     */
    private BigDecimal lstPrice;
    /**
     * 计量单位
     */
    private String uom;
    /**
     * 序号
     */
    private Integer seqNum;
    /**
     * 商品分类Id
     */
    private String prodCatId;
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 商品状态
     */
    private String prodStatus;

    /**
     * 折率
     */
    private BigDecimal discRate;
    /**
     * 采购税率
     */
    private BigDecimal taxRate;
    /**
     * 分销税率
     */
    private BigDecimal wsTaxRate;
    /**
     * 可退率
     */
    private BigDecimal rtnaRate;
    /**
     * 采购单价
     */
    private BigDecimal unitPrice;
    /**
     * 折后价
     */
    private BigDecimal fnlPrice;

    /**
     * 市场单价
     */
    private BigDecimal mkUnitPrice;

    /**
     * 市场单价
     */
    private BigDecimal mkv;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 金额
     */
    private BigDecimal val;

    /**
     * 税款
     */
    private BigDecimal tax;
}
