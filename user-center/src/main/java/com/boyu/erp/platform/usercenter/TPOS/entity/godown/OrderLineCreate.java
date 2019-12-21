package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import com.boyu.erp.platform.usercenter.TPOS.entity.common.ExtendProps;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.OrderCommon;
import lombok.Data;

/**
 * 类描述:
 *
 * @Description: 入库单商品信息 对象
 * @auther: CLF
 * @date: 2019/11/6 11:59
 */
@Data
public class OrderLineCreate extends OrderCommon {
    /**
     * 商品属性
     */
    private String skuProperty;
    /**
     * 采购价
     */
    private String purchasePrice;
    /**
     * 零售价
     */
    private String retailPrice;
    /**
     * 自定义属性
     */
    private ExtendProps extendProps;
}
