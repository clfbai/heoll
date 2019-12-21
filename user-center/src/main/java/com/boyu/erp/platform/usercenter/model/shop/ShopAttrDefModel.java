package com.boyu.erp.platform.usercenter.model.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopAttrDefModel extends ShopAttrDef {

    /**
     * add update delete 参数类型
     */
    private String attrDeftype;

}
