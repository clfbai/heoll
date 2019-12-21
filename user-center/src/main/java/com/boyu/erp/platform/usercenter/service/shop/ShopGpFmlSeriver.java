package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopGpFml;
import com.boyu.erp.platform.usercenter.vo.shop.ShopGpFmlVo;

import java.util.List;

/**
 * 类描述:
 *
 * @Description 门店普通促销公式接口
 * @auther: CLF
 * @date: 2019/8/23 9:46
 */
public interface ShopGpFmlSeriver {
    List<ShopGpFmlVo> getShopGpFml(ShopGpFml shop);
}
