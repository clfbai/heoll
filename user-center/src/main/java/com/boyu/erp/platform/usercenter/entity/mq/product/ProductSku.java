package com.boyu.erp.platform.usercenter.entity.mq.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述:
 *
 * @Description: 商品
 * @auther: CLF
 * @date: 2019/9/24 14:58
 */
@Data
@NoArgsConstructor
public class ProductSku  implements Serializable {
    private Long prodId;
    /**
     * 否 商品规格编号
     */
    private String id;

    /**
     * 否 SKU编码 (prod_code 商品代码)
     */
    private String skuCode;
    /**
     * 是 条码
     */
    private String barcode = this.skuCode;

    /**
     * 是 颜色(颜色名称)
     */
    private String color;
    /**
     * 否 尺码
     */
    private String size;
    /**
     * 否 杯型
     */
    private String cup;

    /**
     * 是 售价（吊牌价）
     */
    private BigDecimal salePrice;

    /**
     * 是 供货价
     */
    private BigDecimal supplyPrice;

}
