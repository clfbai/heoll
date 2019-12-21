package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import com.boyu.erp.platform.usercenter.vo.shop.PayModeVo;

import java.util.List;

public interface PayModeMaaper {
    int deleteByPrimaryKey(Integer payModeId);



    int insertSelective(PayMode record);

    PayMode selectByPrimaryKey(Integer payModeId);

    int updateByPrimaryKeySelective(PayMode record);

    int updateByPrimaryKey(PayMode record);

    /**
     * 功能描述: 查询支付方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 17:40
     */
    List<PayModeVo> getPayModel(PayMode mode);
}