package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * loc_bst (货位装箱库存 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class LocBst implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 实际库存
     */
    private Integer stkOnHand;

    /**
     * 预期库存
     */
    private Integer qtyExpd;

    /**
     * 已配库存
     */
    private Integer qtyCmtd;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 装箱代码
     */
    private String boxCode;

    /**
     * 货位ID
     */
    private Long locId;
}