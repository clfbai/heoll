package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Money {
    /**
     * 总数量
     */
    private String ttlQty;
    /**
     * 总金额
     */
    private String ttlVal;
}
