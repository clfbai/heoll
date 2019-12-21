package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import com.boyu.erp.platform.usercenter.vo.shop.ShopEmpVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopEmpMapper {
    int deleteByPrimaryKey(ShopEmp key);

    int insert(ShopEmp record);

    int insertSelective(ShopEmp record);

    ShopEmp selectByPrimaryKey(ShopEmp key);

    int updateByPrimaryKeySelective(ShopEmp record);

    int updateByPrimaryKey(ShopEmp record);

    /**
     * 功能描述: 查询门店店员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:23
     */
    List<ShopEmpVo> getShopEmp(Shop shop);

    /**
     * 批量增加员工
     */
    int addList(@Param("list") List<ShopEmp> list);
}