package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * tfn
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class StbDtlVo extends CommonDtl implements Serializable {

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 商品品种Id（product）
     */
    private Long prodClsId;
    /**
     * 商品代码（product）
     */
    private String prodCode;
    /**
     * 商品名称（product）
     */
    private String prodName;
    /**
     * 行号
     */
    private Long lineNum;
    /**
     * 排号
     */
    private Long rowNum;

    /**
     * 预期数量
     */
    private BigDecimal expdQty;

    /**
     * 返利
     */
    private BigDecimal rwd;

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
     * 计量单位（中文）
     */
    private String uomCp;
    /**
     * 颜色
     */
    private String colorName;

    /**
     * 规格
     */
    private String specName;

    /**
     * 版型名
     */
    private String edition;
    /**
     * 版型名（中文）
     */
    private String editionCp;

    /**
     * 产品线(存储仓库)
     */
    private String prodLine;

    /**
     * 前端展示名：预定义货位
     * 数据库名：样品编号
     */
    private String sampleNum;
}