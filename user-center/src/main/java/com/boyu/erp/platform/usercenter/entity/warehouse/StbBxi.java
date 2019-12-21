package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * stb_bxi (库存单配码 表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class StbBxi implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 颜色ID
     */
    private Long colorId;

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
    private Long satId;

    /**
     * 每箱数量
     */
    private Float unitQty;

    /**
     * 预期箱数
     */
    private Long expdBox;

    /**
     * 箱数
     */
    private Long box;

    /**
     * 备注
     */
    private String remarks;

}