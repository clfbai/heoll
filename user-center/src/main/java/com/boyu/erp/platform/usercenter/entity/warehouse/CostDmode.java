package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;

/**
 * 影响成本出库方式
 * @author HHe
 * @date 2019/8/29 16:03
 */
@Data
public class CostDmode implements Serializable {
    /**
     * 组织Id
     */
    private String unitId;
    /**
     * 出库方式
     */
    private String delivMode;
}
