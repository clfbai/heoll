package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;

import java.util.List;

/**
 * 类描述:  币种接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 20:17
 */
public interface CurrTypeServicer {
    List<CurrType> getCurrTyrp(CurrType currType);
}
