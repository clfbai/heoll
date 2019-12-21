package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.shop.ShopPayMode;
import com.boyu.erp.platform.usercenter.vo.shop.ShopPayVo;

import java.util.List;

public interface ShopPayModeMapper {
    int deleteByPrimaryKey(ShopPayMode key);

    int insert(ShopPayMode record);

    int insertSelective(ShopPayMode record);

    ShopPayMode selectByPrimaryKey(ShopPayMode key);

    int updateByPrimaryKeySelective(ShopPayMode record);

    int updateByPrimaryKey(ShopPayMode record);
    /**
     *
     * 功能描述: 门店付款方式查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:50
     */
    List<ShopPayVo> getShopPayMode(Shop shop);
}