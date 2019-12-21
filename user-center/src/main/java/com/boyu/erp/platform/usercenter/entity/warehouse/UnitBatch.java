package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * unit_batch (组织批次表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class UnitBatch implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 批次Id
     */
    private String batchId;

    /**
     * 商品Id
     */
    private Long prodId;

    /**
     * 组织Id(仓库所属组织Id)
     */
    private Long unitId;

    /**
     * 生产商品数量
     */
    private Integer productionQuantity;

    /**
     * 采购商品数量
     */
    private Integer purchaseQuantity;

    /**
     * 销售数量
     */
    private Integer marketQuantity;

    /**
     * 剩余数量
     */
    private Integer surplusQuantity;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 仓库Id(对批次逻辑本身不重要)
     */
    private Long warehId;

    /**
     * 入库单编号(对批次逻辑而言没什么用)
     */
    private String docNum;
    /**
     * 批次产生类型(pe 采购入库,pn 生产入库)
     */
    private String batchCreatType;

    /**
     * 采购或生产价格
     */
    private Float price;

    private Integer number;
}