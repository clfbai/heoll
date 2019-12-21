package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttr;

import java.util.List;

/**
 * 类描述: 门店属性接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/22 17:47
 */
public interface ShopAttrServicer {

    int addShopAttr(ShopAttr shopAttr);

    int addShopAttr(List<ShopAttr> shopAttrList);
}
