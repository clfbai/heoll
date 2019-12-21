package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;
import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import com.boyu.erp.platform.usercenter.entity.shop.PayModeCurr;
import com.boyu.erp.platform.usercenter.mapper.shop.PayModeCurrMapper;
import com.boyu.erp.platform.usercenter.mapper.shop.PayModeMaaper;
import com.boyu.erp.platform.usercenter.model.shop.PayModelS;
import com.boyu.erp.platform.usercenter.service.shop.PayModelServicer;
import com.boyu.erp.platform.usercenter.vo.shop.PayModeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 支付方式服务
 * @auther: CLF
 * @date: 2019/8/20 17:37
 */
@Slf4j
@Service
@Transactional
public class PayModelServerImpl implements PayModelServicer {
    @Autowired
    private PayModeMaaper payModeMaaper;
    @Autowired
    private PayModeCurrMapper payModeCurrMapper;


    @Override
    public List<PayModeVo> getParyMode(PayMode mode) {
        return payModeMaaper.getPayModel(mode);
    }

    /**
     * 功能描述: 查询支付方式币种明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 19:26
     */
    @Override
    public List<CurrType> getParyModeCurr(PayMode mode) {
        return payModeCurrMapper.getParyModeCurr(mode.getPayModeId());
    }

    /**
     * 功能描述: 增加付款方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 20:03
     */
    @Override
    public int addPayModel(PayModelS mode) {
        PayMode payMode = mode.getPayMode();
        int a = payModeMaaper.insertSelective(payMode);
        if (CollectionUtils.isNotEmpty(mode.getAddPayModeCurrList())) {
            for (PayModeCurr curr : mode.getAddPayModeCurrList()) {
                curr.setPayModeId(payMode.getPayModeId());
                a += payModeCurrMapper.insert(curr);
            }
        }
        return a;
    }

    /**
     * 功能描述:  修改支付方式明细
     *
     * @param: mode
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 10:53
     */
    @Override
    public int updatePayModelCurr(PayModelS mode) {
        return this.updateCurr(mode);
    }

    /**
     * 功能描述: 修改支付方式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 11:05
     */
    @Override
    public int updatePayModel(PayModelS mode) {
        //判断有无明细修改
        int a = this.updateCurr(mode);
        //修改支付方式
        if (mode.getPayMode() != null && mode.getPayMode().getPayModeId() != null) {
            a += payModeMaaper.updateByPrimaryKeySelective(mode.getPayMode());
        }
        //删除支付方式
        if (mode.getDeletePayModeId() != null && mode.getDeletePayModeId() > 0) {
            a += payModeMaaper.deleteByPrimaryKey(mode.getDeletePayModeId());
            a += payModeCurrMapper.deletepayModeCurr(mode.getDeletePayModeId());
        }
        return a;
    }


    public int updateCurr(PayModelS mode) {
        int a = 0;
        if (CollectionUtils.isNotEmpty(mode.getAddPayModeCurrList())) {
            for (PayModeCurr c : mode.getAddPayModeCurrList()) {
                a += payModeCurrMapper.insert(c);
            }
        }
        if (CollectionUtils.isNotEmpty(mode.getDeletePayModeCurrList())) {
            for (PayModeCurr c : mode.getDeletePayModeCurrList()) {
                a += payModeCurrMapper.deleteByPrimaryKey(c);

            }
        }
        return a;
    }
}
