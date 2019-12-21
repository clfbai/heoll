package com.boyu.erp.platform.usercenter.TPOS.common.goods.singgoods;

import com.boyu.erp.platform.usercenter.TPOS.common.goods.Item;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "request")
public class SingleItem {
    /**
     * add|update, 必填
     */
    private String actionType;
    /**
     * 仓库编码, string (50)，必填 ，统仓统配等无需ERP指定仓储编码的情况填OTHER
     */
    private String warehouseCode;
    /**
     * 货主编码, string (50) , 必填
     */
    private String ownerCode;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * /**供应商名称
     */
    private String supplierName;

    private Item item;
}
