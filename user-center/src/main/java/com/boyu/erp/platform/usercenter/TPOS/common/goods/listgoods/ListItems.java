package com.boyu.erp.platform.usercenter.TPOS.common.goods.listgoods;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 类描述:
 *
 * @Description: 商品集合
 * @auther: CLF
 * @date: 2019/11/26 10:20
 */
@Data
@XmlRootElement(name = "request")
public class ListItems {
    /**
     * add|update, 必填
     */
    private String actionType;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 货主编码
     */
    private String ownerCode;

    private Items items;
}
