package com.boyu.erp.platform.usercenter.model.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: boyu-platform_text
 * @description: 生成商品代码所有字段
 * @author: clf
 * @create: 2019-06-21 10:26
 */
@Data
@ToString
@NoArgsConstructor
public class ProductCodeModel implements Serializable {
    /**
     * 商品品种代码
     */
    private String prodClsCode;
    /**
     * 规格代码
     * 查询复制
     */
    private String specCode;
    /**
     * 颜色代码
     * 查询复制
     */
    private String colorCode;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 商品类别id
     */
    private String prodCatId;
    /**
     * 四位年份
     */
    private String year4;
    /**
     * 两位年份
     */
    private String year2;
    /**
     * 一位年份
     */
    private String year1;
    /**
     * 款/楦号
     */
    private String model;
    /**
     * 子款号
     */
    private String subModel;
    /**
     * 季节编号
     */
    private String season;
    /**
     * 营销系列
     */
    private String mktSort;
    /**
     * 营销类别
     */
    private String mktType;

    /**
     * 版型
     */
    private String edition;

    /**
     * 规格组id
     */
    private String specGrpId;

    /**
     * 规格编号
     */
    private String specNum;

    /**
     * 商品品种编号
     * 暂时未找到  无需赋值
     */
    private String prodClsNum = "01";

}
