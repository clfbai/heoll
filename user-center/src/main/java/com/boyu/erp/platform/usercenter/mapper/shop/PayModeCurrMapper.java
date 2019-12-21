package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;
import com.boyu.erp.platform.usercenter.entity.shop.PayModeCurr;

import java.util.List;

public interface PayModeCurrMapper {
    int deleteByPrimaryKey(PayModeCurr key);

    int insert(PayModeCurr record);

    int insertSelective(PayModeCurr record);

    /**
     * 功能描述: 查询支付方式明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 19:27
     */
    List<CurrType> getParyModeCurr(Integer payModeId);

    /**
     * 功能描述: 删除支付方式时删除支付明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 11:29
     */
    int deletepayModeCurr(Integer payModeId);
}