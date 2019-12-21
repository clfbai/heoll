package com.boyu.erp.platform.usercenter.vo.goods;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductWindowVo implements Serializable {
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 商品品种Id
     */
    private Long prodClsId;
    /**
     * 商品分类Id
     */
    private  String prodCatId;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 规格
     */
    private String specId;
    /**
     * 零售单价
     */
    private BigDecimal rtUnitPrice;
    /**
     * 分销单价
     */
    private BigDecimal wsUnitPrice;
    /**
     * 品牌Id
     */
    private String brandId;
    /**
     * 商品品种状态
     */
    private String prodStatus;


    /**
     * 是否系统管理员
     */
    private String isAdmin="F";


    private Long unitId;
}
