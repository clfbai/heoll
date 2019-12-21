package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;

import java.io.Serializable;

/**
 * shop_attr 门店自定义属性表
 *
 * @author
 */
@Data
public class ShopAttr implements Serializable {
    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 门店Id
     */
    private Long shopId;

    /**
     * 属性类别
     */
    private String attrType;

    private static final long serialVersionUID = 1L;

}