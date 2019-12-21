package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopGpFml;
import com.boyu.erp.platform.usercenter.vo.shop.ShopGpFmlVo;

import java.util.List;

public interface ShopGpFmlMapper {
    int deleteByPrimaryKey(ShopGpFml key);

    int insert(ShopGpFml record);

    int insertSelective(ShopGpFml record);

    ShopGpFml selectByPrimaryKey(ShopGpFml key);

    int updateByPrimaryKeySelective(ShopGpFml record);

    int updateByPrimaryKey(ShopGpFml record);

    /**
     * 功能描述: 根据门店Id查询门店普通促销公式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 9:51
     */
    List<ShopGpFmlVo> getShopGpFml(ShopGpFml shopGpFml);
}