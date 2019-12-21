package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 出库单VO
 * 包含：出库任务集合；
 */
@Data
@NoArgsConstructor
public class StbGdnVO extends GdnVO implements Serializable {
    /**
     * 组织ID
     * NOT NULL
     */
      private Long unitId;

    /**
     * 库存单编号(仓库单据编号)
     * NOT NULL
     */
    private String stbNum;
    /**
     * 单据日期(接收时间) ○
     * NOT NULL
     */
    private Date docDate;

    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 仓库ID ○
     * NOT NULL
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
     * 中转方ID○
     */
    private String tranUnitId;

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
     * 出入库类别("d","r")○
     * NOT NULL
     */
    private String drType;

    /**
     * 原始单据类别○
     */
    private String srcDocType;

    /**
     * 原始单据组织ID○
     */
    private Long srcDocUnitId;

    /**
     * 原始单据编号○
     */
    private String srcDocNum;

    /**
     * 合同号
     */
    private String cntrNum;

    /**
     * 总库存单编号○
     */
    private String ldgStbNum;

    /**
     * 货位管理("t","f")○
     * NOT NULL
     * 请求wareh_a
     */
    private String locAdopted;
    /**
     * 货位ID
     * 对应wareh_a.deliv_loc_id
     */
    private Integer locId;

    /**
     * 启用配码("t","f")○
     * NOT NULL
     */
    private String bxiEnabled;


    /**
     * 启用装箱("t","f")○
     * NOT NULL
     * 对应wareh_dmode
     */
    private String boxReqd;

    /**
     * 预定装箱("t","f")○
     * NOT NULL
     * 对应wareh_dmode
     */
    private String boxSchd;

    /**
     * 预期总数量○
     * NOT NULL
     */
    private BigDecimal ttlExpdQty;

    /**
     * 预期总箱数○
     */
    private Long ttlExpdBox;

    /**
     * 总数量○
     * NOT NULL
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数○
     */
    private Long ttlBox;

    /**
     * 总包裹数
     */
    private Long ttlPack;

    /**
     * 总金额○
     * NOT NULL
     */
    private BigDecimal ttlVal;

    /**
     * 总返利
     */
    private BigDecimal ttlRwd;

    /**
     * 总税款○
     * NOT NULL
     */
    private BigDecimal ttlTax;

    /**
     * 总市值○
     */
    private BigDecimal ttlMkv;

    /**
     * 总成本
     */
    private BigDecimal ttlCost;

    /**
     * 约定会计日期("t","f")○
     * NOT NULL
     */
    private String fsclDateAptd;

    /**
     * 即时结算("t","f")○
     * NOT NULL
     * 对应wareh_dmode
     */
    private String instStl;

    /**
     * 桥接方式("d","t")○
     * NOT NULL
     */
    private String brdgMode;

    /**
     * 改变成本("t","f")○
     * NOT NULL
     * 退购出库和采购入库单会被标识为改变成本的单据
     */
    private String costChg;

    /**
     * 过账控制("u","p","d")
     * NOT NULL（null）
     */
    private String postCtrl;

    /**
     * 操作员ID
     * NOT NULL
     * 登录用户id
     */
    private Long oprId;

    /**
     * 操作时间
     * NOT NULL
     */
    private Timestamp opTime;

    /**
     * 已生效("t","f")○
     * NOT NULL
     */
    private String effective;

    /**
     * 挂起("t","f")○
     * NOT NULL
     */
    private String suspended;

    /**
     * 撤销("t","f")○
     * NOT NULL
     */
    private String cancelled;

    /**
     * 已冲单("t","f")○
     * NOT NULL
     */
    private String reversed;

    /**
     * 是否冲单("t","f")○
     * NOT NULL
     */
    private String isRev;

    /**
     * 原单号
     */
    private String orgStbNum;

    /**
     * 备注○
     */
    private String remarks;

    /**
     * 集货区ID
     */
    private Long clnAreaId;

    /**
     * 商品明细
     */
    private List<StbDtlVo> stbDtlVos;
    /**
     * 关联明细单号
     */
    private String dtlNum;
}
