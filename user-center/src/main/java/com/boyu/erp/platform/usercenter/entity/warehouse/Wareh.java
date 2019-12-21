package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * wareh  (仓库表)
 * @author 
 */
@Data
@NoArgsConstructor
public class Wareh implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 仓库属性
     */
    private String warehProp;

    /**
     * 库存管理
     */
    private String stkAdopted;

    /**
     * 唯一码管理
     */
    private String uidAdopted;

    /**
     * 允许负动存
     */
    private String negAtk;

    /**
     * 允许负库存
     */
    private String negStk;

    /**
     * 配码库存管理
     */
    private String astAdopted;

    /**
     * 装箱库存管理
     */
    private String bstAdopted;

    /**
     * 货位管理
     */
    private String locAdopted;

    /**
     * 允许货位负动存
     */
    private String negLocAtk;

    /**
     * 允许货位负库存
     */
    private String negLocStk;

    /**
     * 发货货位ID
     */
    private Integer delivLocId;

    /**
     * 收货货位ID
     */
    private Integer rcvLocId;

    /**
     * 打包货位ID
     */
    private Integer pckLocId;

    /**
     * 拆包货位ID
     */
    private Integer upkLocId;

    /**
     * 启用唯一码验收
     */
    private String acptUidReqd;

    /**
     * 启用唯一码分储
     */
    private String paUidReqd;

    /**
     * 启用唯一码分拣
     */
    private String pickUidReqd;

    /**
     * 启用唯一码复核
     */
    private String rckUidReqd;

    /**
     * 启用唯一码装箱
     */
    private String boxUidReqd;

    /**
     * 会计组织ID
     */
    private Long fsclUnitId;

    /**
     * 成本组ID
     */
    private Long costGrpId;

    /**
     * 产权方ID
     */
    private Long propOwnerId;

    /**
     * 仓库状态
     */
    private String warehStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 集货区管理
     */
    private String clnAreaAdopted;

    /**
     * 启用分货复核
     */
    private String clnRckReqd;


}