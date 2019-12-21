package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * rgo_type 调配单类别实体
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class RgoType implements Serializable {
    /**
     * 调配单类别
     */
    private String rgoType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定中转仓库
     */
    private String tranWarehReqd;

    /**
     * 须指定发货仓库
     */
    private String delivWarehReqd;

    /**
     * 退购合同自动生成
     */
    private String prcAutoGen;

    /**
     * 退购合同自动审核
     */
    private String prcAutoChk;

    /**
     * 须指定收货仓库
     */
    private String rcvWarehReqd;

    /**
     * 采购合同自动生成
     */
    private String pucAutoGen;

    /**
     * 采购合同自动审核
     */
    private String pucAutoChk;

    /**
     * 差异裁定方
     */
    private String drDiffJgd;

    /**
     * 差异裁定方可选
     */
    private String drDiffJgdAlt;

    /**
     * 固定单价
     */
    private String fixedUnitPrice;
    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 启用配码可选
     */
    private String bxiEnabledAlt;

    /**
     * 退销合同类别
     */
    private String srcType;

    /**
     * 销售合同类别
     */
    private String slcType;

    /**
     * 活动
     */
    private String active;

}