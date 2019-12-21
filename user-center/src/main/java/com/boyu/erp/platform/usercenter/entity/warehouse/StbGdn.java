package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class StbGdn {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 库存单编号(仓库单据编号)
     */
    private String stbNum;
    /**
     * 单据日期(接收时间)
     */
    private Date docDate;

    /**
     * 会计日期
     */
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

    //----------------------gdn--------------------------

    /**
     * 出库单编号
     */
    private String gdnNum;

    /**
     * 出库方式
     */
    private String delivMode;

    /**
     * 会计出库方式
     */
    private String fsclDelivMode;

    /**
     * 收货方ID
     */
    private Long rcvUnitId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 收货会计组织ID
     */
    private Long rcvFsclUnitId;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

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
     * 启用分拣
     */
    private String pickReqd;

    /**
     * 启用复核
     */
    private String rckReqd;

    /**
     * 启用派车
     */
    private String vehReqd;

    /**
     * 派车单编号
     */
    private String vwcNum;

    /**
     * 启用托运
     */
    private String cnsnReqd;

    /**
     * 托运单号
     */
    private String csbNum;

    /**
     * 拣货人ID
     */
    private Long ftchrId;

    /**
     * 拣货开始时间
     */
    private Date ftchStTime;

    /**
     * 拣货结束时间
     */
    private Date ftchFinTime;

    /**
     * 发货人ID
     */
    private Long dlvrId;

    /**
     * 发货时间
     */
    private Timestamp delivTime;

    /**
     * 托运人ID
     */
    private Long cngnrId;

    /**
     * 托运时间
     */
    private Date cnsnTime;

    /**
     * 进度
     */
    private String progress;
}
