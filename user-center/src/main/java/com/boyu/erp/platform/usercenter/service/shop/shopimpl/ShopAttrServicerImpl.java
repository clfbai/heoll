package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttr;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopAttrMapper;
import com.boyu.erp.platform.usercenter.service.shop.ShopAttrServicer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类描述: 门店属性服务
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/22 17:47
 */
public class ShopAttrServicerImpl implements ShopAttrServicer {
    @Autowired
    private ShopAttrMapper shopAttrMapper;

    /**
     * 功能描述: 单条增加门店属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 17:50
     */
    @Override
    public int addShopAttr(ShopAttr shopAttr) {
        return shopAttrMapper.insertSelective(shopAttr);
    }

    /**
     * 功能描述: 多条增加门店属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/22 17:50
     */
    @Override
    public int addShopAttr(List<ShopAttr> shopAttrList) {
        return shopAttrMapper.addList(shopAttrList);
    }
}
