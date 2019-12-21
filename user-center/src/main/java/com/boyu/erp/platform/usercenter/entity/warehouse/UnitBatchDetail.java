package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * unit_batch_detail (组织批次号扭转明细表)
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class UnitBatchDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    private Long id;

    /**
     * 批次Id
     */
    private String batchId;

    /**
     * 批次序号
     */
    private Long batchNumber;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 采购商Id(如果门店卖给顾客则为缺省值为-1)
     */
    private Long vendeeId;

    /**
     * 供应商Id
     */
    private Long venderId;

    /**
     * 采购数量
     */
    private Integer purchaseQuantity;

    /**
     * 销售数量
     */
    private Integer quantitymarketQuantity;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 生成批次号方式
     */
    private String batchType;

    /**
     * 批次号扭转类型
     */
    private String batchReverse;


    /**
     * 批次价格(采购成本价格)
     */
    private Float price;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 操作用户
     */
    private Long oprId;



}