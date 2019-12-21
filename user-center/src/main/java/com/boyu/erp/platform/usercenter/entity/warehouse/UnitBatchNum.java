package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * unit_batch_num (组织批次序号表)
 *
 * @author
 */
@Data
@ToString
public class UnitBatchNum implements Serializable {
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
     * 批次序号
     */
    private Integer batchNumber;

    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 采购商Id(顾客购买为缺省组织Id -1 )
     */
    private Long vendeeId;

    /**
     * 供应商Id( )
     */
    private Long venderId;
    /**
     * 采购商品数量
     */
    private Integer purchaseQuantity;

    /**
     * 销售数量
     */
    private Integer marketQuantity;

    /**
     * 剩余商品数量
     */
    private Integer surplusQuantity;

    /**
     * 顾客可退数量(顾客可退给门店或组织)
     */
    private Integer returnableShopQuantity;

    /**
     * 组织可退数量(门店可退给组织或组织可退给组织)
     */
    private Integer returnableUnitQuantity;

    /**
     * 批次价格(采购成本价格)
     */
    private Float price;

    /**
     * 'nol 正常批次号 spi 特殊批次号'
     */
    private String batchType;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 入库单编号(不重要字段)
     */
    private String docNum;
    /**
     * 单据类型
     */
    private String docType;

    public UnitBatchNum() {
    }

    public UnitBatchNum(Long unitId, String docNum, String docType) {
        this.unitId = unitId;
        this.docNum = docNum;
        this.docType = docType;
    }

    public UnitBatchNum(Long prodId, Long vendeeId, Long venderId) {
        this.prodId = prodId;
        this.vendeeId = vendeeId;
        this.venderId = venderId;
    }
}