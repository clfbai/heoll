package com.boyu.erp.platform.usercenter.entity.mq.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname RgoOrderAdd
 * @Description TODO
 * @Date 2019/10/25 9:57
 * @Created by wz
 * 发送/接收调配单消息对象
 */
@Data
@NoArgsConstructor
public class RgoOrderAdd implements Serializable {

    /**
     * 订单id
     */
    private String id;
    /**
     * 供货商编号
     */
    private String supplierCode;
    /**
     * 调入门店
     */
    private String shopId;
    /**
     * 调出门店
     */
    private String sourceShopId;
    /**
     * 整单备注
     */
    private String remarks;
    /**
     * 订单商品
     */
    private List<RgoOrderItems> items = new ArrayList<>();

}
