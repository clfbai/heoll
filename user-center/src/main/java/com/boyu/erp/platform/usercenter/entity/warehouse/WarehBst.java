package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * wareh_bst (仓库装箱库存)
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class WarehBst  implements Serializable {
    /**
     * 仓库Id
     */
    private Long warehId;

    /**
     * 装箱代码
     */
    private String boxCode;
    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 实际库存
     */
    private Float stkOnHand;

    /**
     * 预期库存
     */
    private Float qtyExpd;

    /**
     * 在途库存
     */
    private Float qtyInTran;

    /**
     * 预订库存
     */
    private Float qtyBkd;

    /**
     * 已配库存
     */
    private Float qtyCmtd;

    private static final long serialVersionUID = 1L;

}