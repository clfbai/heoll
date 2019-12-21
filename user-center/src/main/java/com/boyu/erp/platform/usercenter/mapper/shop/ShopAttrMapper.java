package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopAttrMapper {
    int deleteByPrimaryKey(ShopAttr key);

    int insert(ShopAttr record);

    int insertSelective(ShopAttr record);

    ShopAttr selectByPrimaryKey(ShopAttr key);

    int updateByPrimaryKeySelective(ShopAttr record);

    int updateByPrimaryKey(ShopAttr record);

    List<ShopAttr> getShopAttr(ShopAttr shopAttr);

    int addList (@Param("list") List<ShopAttr> shopAttrList);

}