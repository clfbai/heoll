package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname WarehSrcInfoVo
 * @Description TODO
 * @Date 2019/10/29 12:03
 * @Created by wz
 */
@Data
@ToString
@NoArgsConstructor
public class WarehSrcInfoVo implements Serializable {

    /**
     * 单据明细编号(合同号)
     */
    private String cntrNum;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Long ttlBox;

    /**
     * 总金额  not null
     */
    private BigDecimal ttlVal;

    /**
     * 总税款
     */
    private BigDecimal ttlTax;

    /**
     * 总市值
     */
    private BigDecimal ttlMkv;

    /**
     * 桥接方式("d","t")
     */
    private String brdgMode;

    /**
     * 中转方ID
     */
    private Long tranUnitId;

    /**
     * 收货方id
     */
    private Long rcvUnitId;

    /**
     * 收货仓库id
     */
    private Long rcvWarehId;

    /**
     * 转送方id
     */
    private Long endUnitId;

    /**
     * 转送方仓库id
     */
    private Long endWarehId;

    /**
     * 发货方id
     */
    private Long delivUnitId;

    /**
     * 发货方仓库id
     */
    private Long delivWarehId;

    /**
     * 始发方id
     */
    private Long stUnitId;

    /**
     * 始发方仓库id
     */
    private Long stWarehId;

    /**
     * 送货方式
     */
    private String delivMthd;

    /**
     * 邮政编码
     */
    private String delivPstd;

    /**
     * 送货地址
     */
    private String delivAddr;
    /**
     * 采购商Id(批次需要)
     */
    private Long vendeeId;
    /**
     * 供应商Id(批次需要)
     */
    private Long venderId;
    /**
     * 供应商是否介入(批次需要)   1 等于true 0 等于 false
     */
    private Integer intervene;

    /**
     * 总返利
     */
    private BigDecimal ttlRwd = new BigDecimal("0");

}