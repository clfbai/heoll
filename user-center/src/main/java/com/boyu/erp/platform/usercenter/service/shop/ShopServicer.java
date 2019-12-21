package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.shop.ShopEmpModel;
import com.boyu.erp.platform.usercenter.model.shop.ShopModel;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.vo.shop.ShopEmpVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopPayVo;
import com.boyu.erp.platform.usercenter.vo.shop.ShopVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 类描述:  门店接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/21 16:21
 */
public interface ShopServicer {

    PageInfo<ShopVo> getShop(Integer page, Integer size, ShopModel shop, SysUser user);

    /**
     * 功能描述: 门店属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:20
     */
    List<Map<String, Object>> getShopAtrrDef(Shop model);

    /**
     * 功能描述:  查询门店店员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:20
     */
    List<ShopEmpVo> getShopEmp(Shop shop);

    /**
     * 功能描述: 查询门店付款方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 19:55
     */
    List<ShopPayVo> getShopPay(Shop shop);

    /**
     * 功能描述: 增加门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:34
     */
    int add(ShopModel shop, SysUser user);

    /**
     * 功能描述: 批量增加门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/29 15:34
     */
    int addList(ShopModel shop);

    /**
     * 功能描述:  修改门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 14:55
     */
    int updateShop(ShopModel shop);

    /**
     * 功能描述:  修改门店店员
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/30 14:55
     */
    int updateShopEmp(ShopEmpModel shop);

    /**
     * 功能描述: 打标删除门店
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/31 16:45
     */
    int deleteShop(ShopModel shop);

    /**
     * 功能描述: 查询门店可选员工(可添加的导购)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/12 11:15
     */
    PageInfo<ShopEmpVo> getEmpVo(Integer page, Integer size, OwnerPrsnlModel model, SysUser user);

}
