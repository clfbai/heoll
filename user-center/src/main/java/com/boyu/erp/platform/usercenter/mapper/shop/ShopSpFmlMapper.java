package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopSpFml;
import com.boyu.erp.platform.usercenter.vo.shop.ShopSpFmlVo;

import java.util.List;

public interface ShopSpFmlMapper {
    int deleteByPrimaryKey(ShopSpFml key);

    int insert(ShopSpFml record);

    int insertSelective(ShopSpFml record);

    ShopSpFml selectByPrimaryKey(ShopSpFml key);

    int updateByPrimaryKeySelective(ShopSpFml record);

    int updateByPrimaryKey(ShopSpFml record);

    /**
     * 功能描述: 查询门店扣点公式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 10:40
     */
    List<ShopSpFmlVo> getShopSpFml(ShopSpFmlVo s);
}