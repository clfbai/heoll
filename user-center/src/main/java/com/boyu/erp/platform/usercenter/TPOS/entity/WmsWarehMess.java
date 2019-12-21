package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 使用WMS系统仓库信息
 *
 * @author
 */
@Data
@NoArgsConstructor
public class WmsWarehMess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 仓库Id
     */
    private Long warehId;

}