package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopRentVal;
import com.boyu.erp.platform.usercenter.vo.shop.ShopRentValVo;

import java.util.List;

public interface ShopRentValMapper {

    int deleteByPrimaryKey(ShopRentVal key);

    int insert(ShopRentVal record);

    int insertSelective(ShopRentVal record);

    ShopRentVal selectByPrimaryKey(ShopRentVal key);

    int updateByPrimaryKeySelective(ShopRentVal record);

    int updateByPrimaryKey(ShopRentVal record);

    /**
     * 功能描述: 查询门店租金
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 10:30
     */
    List<ShopRentValVo> getShopRentVal(ShopRentValVo shopRentVal);
}