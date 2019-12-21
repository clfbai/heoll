package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * tfn_bxi
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class TfnBxiVo implements Serializable {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 调拨单号
     */
    private String tfnNum;

    /**
     * 商品品种ID
     */
    private Integer prodClsId;

    /**
     * 颜色ID
     */
    private Integer colorId;

    /**
     * 版型
     */
    private String edition;

    /**
     * 特征串
     */
    private String egnStr;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 配码ID
     */
    private Short satId;

    /**
     * 每箱数量
     */
    private BigDecimal unitQty;

    /**
     * 箱数
     */
    private Integer box;

    /**
     * 发货箱数
     */
    private Integer delivBox;

    /**
     * 到货箱数
     */
    private Integer rcvBox;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 品种代码
     */
    private String prodClsCode;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private String seqNum;

    /**
     * 计量单位
     */
    private String uom;

    /**
     * 色码
     */
    private String colorCode;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 版型标注
     */
    private String editionCmt;
}