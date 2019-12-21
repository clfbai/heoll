package com.boyu.erp.platform.usercenter.model.shop;

import com.boyu.erp.platform.usercenter.entity.shop.PayMode;
import com.boyu.erp.platform.usercenter.entity.shop.PayModeCurr;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述: 支付方式数据模型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/20 17:54
 */
@Data
@NoArgsConstructor
public class PayModelS implements Serializable {
    /**
     * 删除付款方式Id
     */
    private Integer deletePayModeId;

    private PayMode payMode;

    private PayModeCurr payModeCurr;

    private List<PayModeCurr> addPayModeCurrList = new ArrayList<>();

    private List<PayModeCurr> deletePayModeCurrList = new ArrayList<>();


    private List<PayMode> addList = new ArrayList<>();
}
