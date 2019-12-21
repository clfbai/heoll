package com.boyu.erp.platform.usercenter.entity.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * stb (库存单 表)
 *
 * @author
 */
@Data
@ToString
public class Stb implements Serializable {
    public Stb() {
    }

    public Stb(Long unitId, String stbNum) {
        this.unitId = unitId;
        this.stbNum = stbNum;
    }

    /**
     * 组织ID
     */
    private Long unitId;
    /**
     * 库存单编号(仓库单据编号)
     */
    private String stbNum;

    /**
     * 单据日期(接收时间)
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date docDate;

    /**
     * 会计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date fsclDate;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 会计组织ID
     */
    private Long fsclUnitId;

    /**
     * 成本组ID
     */
    private Long costGrpId;

    /**
     * 中转方ID
     */
    private Long tranUnitId;

    /**
     * 挂账仓库ID
     */
    private Long hldnWarehId;

    /**
     * 挂账成本组ID
     */
    private Long hldnCostGrpId;

    /**
     * 逻辑仓库ID
     */
    private Long lgcWarehId;

    /**
     * 出入库类别("d","r")
     */
    private String drType;

    /**
     * 原始单据类别
     */
    private String srcDocType;

    /**
     * 原始单据组织ID
     */
    private Long srcDocUnitId;

    /**
     * 原始单据编号
     */
    private String srcDocNum;

    /**
     * 合同号
     */
    private String cntrNum;

    /**
     * 总库存单编号
     */
    private String ldgStbNum;

    /**
     * 货位管理("t","f")
     */
    private String locAdopted;

    /**
     * 货位ID
     */
    private Long locId;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 启用装箱("t","f")
     */
    private String boxReqd;

    /**
     * 预定装箱("t","f")
     */
    private String boxSchd;

    /**
     * 预期总数量
     */
    private BigDecimal ttlExpdQty;

    /**
     * 预期总箱数
     */
    private BigDecimal ttlExpdBox;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private BigDecimal ttlBox;

    /**
     * 总包裹数
     */
    private Long ttlPack;

    /**
     * 总金额
     */
    private BigDecimal ttlVal;

    /**
     * 总返利
     */
    private BigDecimal ttlRwd;

    /**
     * 总税款
     */
    private BigDecimal ttlTax;

    /**
     * 总市值
     */
    private BigDecimal ttlMkv;

    /**
     * 总成本
     */
    private BigDecimal ttlCost;

    /**
     * 约定会计日期("t","f")
     */
    private String fsclDateAptd;

    /**
     * 即时结算("t","f")
     */
    private String instStl;

    /**
     * 桥接方式("d","t")
     */
    private String brdgMode;

    /**
     * 改变成本("t","f")
     */
    private String costChg;

    /**
     * 过账控制("u","p","d")
     */
    private String postCtrl;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date opTime;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 挂起("t","f")
     */
    private String suspended;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 已冲单("t","f")
     */
    private String reversed;

    /**
     * 是否冲单("t","f")
     */
    private String isRev;

    /**
     * 原单号
     */
    private String orgStbNum;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 集货区ID
     */
    private Long clnAreaId;

    /**
     * 原始单据类别中文
     */
    private String srcDocTypeCp;

}