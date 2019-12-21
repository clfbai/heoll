package com.boyu.erp.platform.usercenter.entity.mq.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:
 *
 * @Description: 商品品种
 * @auther: CLF
 * @date: 2019/9/24 14:58
 */
@Data
@NoArgsConstructor
public class ProductMessage  implements Serializable {
    /**
     * 商品品种编号
     */
    private Long prodClsId;

    /**
     * 是	商品编号
     */
    private String id;

    /**
     * 是	商品名称
     */
    private String title;

    /**
     * 管理组织Id
     */
    private String supplierCode;
    /**
     * 商品图片主图，存在多图就以数组形式返回，中间用逗号隔开(设置默认图片)暂时不管
     */
    private String pic_url;
    /**
     * 否	商品详情
     */
    private String desc;
    /**
     * 否	移动端的商品详情
     */
    private String wapDesc;

    /**
     * 是	商品创建时间，如：2009-10-22 14:22:06
     */
    private String createTime;
    /**
     * 是 商品编码
     */
    private String code;
    /**
     * 是 助记码
     */
    private String mnemonicCode;
    /**
     * 否 上市时间
     */
    private String timeToMarket;
    /**
     * 否 年份
     */
    private Integer year;
    /**
     * 否 重量
     */
    private BigDecimal weight;
    /**
     * 否 体积
     */
    private BigDecimal volume;
    /**
     * 是 品牌
     */
    private String brandName;
    /**
     * 品牌代码
     */
    private String brandCode = "THJ";
    /**
     * 否 计量单位
     */
    private String measureUnit;
    /**
     * 否 季节(适销季节)
     */
    private String season;
    /**
     * 否 功能卖点
     */
    private String sellingPoint;
    /**
     * 否 产品风格
     */

    private String productStyle;
    /**
     * 否 面料
     */
    private String material;
    /**
     * 否 厚薄度
     */
    private String thickness;
    /**
     * 否 花色/结构/杯型
     */
    private String mainStyle;
    /**
     * 否 排扣/腰头/克重
     */
    private String subStyle;
    /**
     * 是 售价（吊牌价）
     */
    private BigDecimal salePrice;
    /**
     * 具体商品
     */
    private List<ProductSku> skus = new ArrayList<>();


}
