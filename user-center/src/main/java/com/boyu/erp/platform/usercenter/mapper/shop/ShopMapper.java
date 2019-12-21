package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.model.shop.ShopModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.vo.shop.ShopEmpVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopVo;

import java.util.List;
import java.util.Map;

public interface ShopMapper {
    int deleteByPrimaryKey(Long shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long shopId);

    //查询是否有门店
    Shop selectByShop(Shop record);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Map<String, Object>> getRoeAndLineMap(Map<String, Object> map);

    /**
     * 功能描述:  查询门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 16:47
     */
    List<ShopVo> selectShop(ShopModel shop);

    /**
     * 功能描述: 查询门店可添加的导购
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/12 11:19
     */
    List<ShopEmpVo> selectShopEmpVo(OwnerPrsnlModel model);
}