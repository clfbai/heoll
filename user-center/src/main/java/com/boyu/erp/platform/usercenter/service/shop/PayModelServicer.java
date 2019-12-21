package com.boyu.erp.platform.usercenter.service.shop;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;
import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import com.boyu.erp.platform.usercenter.model.shop.PayModelS;
import com.boyu.erp.platform.usercenter.vo.shop.PayModeVo;

import java.util.List;

/**
 * 类描述: 支付方式接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 17:35
 */
public interface PayModelServicer {
    /**
     * 功能描述: 支付方式查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 17:44
     */
    List<PayModeVo> getParyMode(PayMode mode);

    /**
     * 功能描述: 查询支付方式明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 17:44
     */
    List<CurrType> getParyModeCurr(PayMode mode);

    /**
     * 功能描述: 增加付款方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 20:03
     */
    int addPayModel(PayModelS mode);

    /**
     * 功能描述:  修改支付方式明细
     *
     * @param: mode
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 10:53
     */
    int updatePayModelCurr(PayModelS mode);

    /**
     * 功能描述: 修改支付方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 11:05
     */
    int updatePayModel(PayModelS mode);
}
