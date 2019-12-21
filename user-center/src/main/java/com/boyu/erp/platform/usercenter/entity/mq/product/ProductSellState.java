package com.boyu.erp.platform.usercenter.entity.mq.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述:
 *
 * @Description: 商品上下架消息对象
 * @auther: CLF
 * @date: 2019/9/27 17:07
 */
@Data
@NoArgsConstructor
public class ProductSellState   implements Serializable {

    /**
     * 商品品种代码
     */
    private String prodClsCode;

    /**
     * 是	商品编号
     */
    private String id;

    /**
     * 1销售中4:已删除5:手动下架
     */
    private Integer sellState;

}
