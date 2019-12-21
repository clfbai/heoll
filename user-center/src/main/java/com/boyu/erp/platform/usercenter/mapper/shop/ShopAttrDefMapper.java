package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;

import java.util.List;

public interface ShopAttrDefMapper {
    int deleteByPrimaryKey(String attrType);

    int insertSelective(ShopAttrDef record);

   List<ShopAttrDef>  selectByPrimaryKey(String attrType);

    int updateByPrimaryKeySelective(ShopAttrDef record);



    /**
     * 功能描述: 查询门店自定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 15:47
     */
    List<ShopAttrDef> getShopAttrDef(ShopAttrDef shopAttrDef);


}