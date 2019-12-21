package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.shop.ShopAttrDefModel;
import com.boyu.erp.platform.usercenter.vo.shop.ShopAttrDefVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ShopAttrDefServicer {

    PageInfo<ShopAttrDef> getShopAttr(Integer page, Integer size, ShopAttrDef shopAttrdef) throws ServiceException;

    /**
     * 功能描述: 增加门店定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:35
     */
    int addShopAttrDef(ShopAttrDef shopAttrdef);

    /**
     * 功能描述:
     *
     * @param: 删除门店定义属性
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:36
     */
    int deleteShopAttrDef(ShopAttrDef shopAttrdef);

    /**
     * 功能描述: 修改门店定义属性(主键不能修改 attrType)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:37
     */
    int updateShopAttrDef(ShopAttrDefModel shopAttrdef);



    /**
     * 功能描述: 门店自定义属性名称和其下拉值集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/28 15:31
     */
    List<ShopAttrDefVo> getShopAttrName();
}
