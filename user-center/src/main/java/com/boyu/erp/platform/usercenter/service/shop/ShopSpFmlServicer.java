package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.vo.shop.ShopSpFmlVo;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 门店扣点公式
 * @auther: CLF
 * @date: 2019/8/23 10:37
 */
public interface ShopSpFmlServicer {
    List<ShopSpFmlVo> getShopSpFml(ShopSpFmlVo s);
}
