package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;

/**
 * wareh_ug_dtl
 * @author 
 */
@Data
public class WarehUgDtl implements Serializable {
    /**
     * 分组id
     */
    private Long ugId;

    /**
     * 仓库Id
     */
    private Long warehId;
}