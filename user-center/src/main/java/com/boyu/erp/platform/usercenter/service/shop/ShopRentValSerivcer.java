package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.vo.shop.ShopRentValVo;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 门店租金接口
 * @auther: CLF
 * @date: 2019/8/23 10:28
 */
public interface ShopRentValSerivcer {

    List<ShopRentValVo> getShopRentVal(ShopRentValVo shopRentVal);
}
