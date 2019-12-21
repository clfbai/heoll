package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

@Data
public class PackageMaterial {
    /**
     * 包材型号
     */
    private String type;
    /**
     * 包材的数量
     */
    private int quantity;
}
