package com.boyu.erp.platform.usercenter.entity.mq.order;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname SrcOrderAdd
 * @Description TODO
 * @Date 2019/12/3 16:17
 * @Created by wz
 */
@Data
public class SrcOrderAdd implements Serializable {

    /**
     * 售后单id
     */
    private String id;

    /**
     * 退货门店id
     */
    private String shopId;

    /**
     * 发起退款的金额
     */
    private BigDecimal amount;

    /**
     * 合同类别
     * 1：试销退货
     * 2：次品退货
     * 3：整单取消（不处理此状态）
     */
    private int type;

    /**
     * 供货商编号
     */
    private String supplierCode;

    /**
     * 售后单创建事件
     */
    private String createTime;

    /**
     * 售后商品信息
     */
    private List<SrcOrderItems> items;
}
