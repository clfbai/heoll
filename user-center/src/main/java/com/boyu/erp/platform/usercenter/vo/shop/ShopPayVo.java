package com.boyu.erp.platform.usercenter.vo.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopPayMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShopPayVo extends ShopPayMode implements Serializable {

    /**
     * 付款名称
     */
    private String payModeName;
    /**
     * 付款名称
     */
    private String payModeCode;
}
