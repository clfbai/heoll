package com.boyu.erp.platform.usercenter.vo.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 商品属性行转列数据模型
 * @author: clf
 * @create: 2019-06-14 17:21
 */
@Data
@ToString
@NoArgsConstructor
public class ProdClsAttrLineVo {
    /**
     * 商品品种Id
     */
    private Long prodClsId;
    /**
     * 商品类别
     */
    private String prodCata;
    /**
     * 文胸外型
     */
    private String barShape;
    /**
     * 是否精品
     */
    private String boutique;
    /**
     * 衣长
     */
    private String clothesLe;
    /**
     * 组合形式
     */
    private String combiningForms;
    /**
     * 适合人群
     */
    private String crowd;
    /**
     * 肩带
     */
    private String braStrap;
    /**
     * 产品部分类
     */
    private String prodStyle;
    /**
     * 厚度
     */
    private String thickness;
    /**
     * 上传HIMALL
     */
    private String uploadHm;
    /**
     * 类型
     */
    private String why;
    /**
     *WMSPU_CODE
     */
    private String wmspuCode;
}
