package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GdnListVO implements Serializable {
    /**
     * 组织ID
     * NOT NULL
     */
    private Long unitId;

    /**
     * 库存单编号(仓库单据编号)
     * NOT NULL-----
     */
    private String stbNum;
    /**
     * 单据日期(接收时间)
     * NOT NULL-------
     */
    private Date docDate;

    /**
     * 会计日期------
     */
    private Date fsclDate;

    /**
     * 仓库ID
     * NOT NULL
     */
    private Long warehId;
    /**
     * 仓库编号
     */
    private String warehCode;
    /**
     * 仓库名称
     */
    private String warehName;
    /**
     * 唯一码管理（ t , f ）
     */
    private String uidAdopted;
//    private String uidAdoptedCN;
    /**
     * 会计组织ID
     */
    private Long fsclUnitId;
    /**
     * 会计组织编号
     */
    private String fsclUnitCode;
    /**
     * 会计组织名称
     */
    private String fsclUnitName;
    /**
     * 成本组ID
     */
    private Long costGrpId;

    /**
     * 成本组名称
     */
    private String costGrpName;

    /**
     * 中转方ID
     */
    private Long tranUnitId;
    /**
     * 中转方编号
     */
    private String tranUnitCode;
    /**
     * 中转方名称
     */
    private String tranUnitName;

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
     * 逻辑仓库编号
     */
    private String lgcWarehCode;
    /**
     * 逻辑仓库名称
     */
    private String lgcWarehName;
    /**
     * 出入库类别("d","r")
     * NOT NULL
     */
    private String drType;

    /**
     * 原始单据类别
     */
    private String srcDocType;
    /**
     * 原始单据类别名称（中文）
     */
//    private String srcDocTypeCN;

    /**
     * 原始单据组织ID
     */
    private Long srcDocUnitId;

    /**
     * 原始单据编号-------------
     */
    private String srcDocNum;

    /**
     * 合同号
     */
    private String cntrNum;

    /**
     * 总库存单编号-----------
     */
    private String ldgStbNum;

    /**
     * 货位管理("t","f")
     * NOT NULL--------
     */
    private String locAdopted;

    /**
     * 货位ID
     */
    private Long locId;
    /**
     * 货位编号
     */
    private String locCode;

    /**
     * 启用配码("t","f")
     * NOT NULL----------
     */
    private String bxiEnabled;

    /**
     * 启用装箱("t","f")
     * NOT NULL-------------------
     */
    private String boxReqd;

    /**
     * 预定装箱("t","f")
     * NOT NULL
     */
    private String boxSchd;

    /**
     * 预期总数量
     * NOT NULL----------
     */
    private BigDecimal ttlExpdQty;

    /**
     * 预期总箱数------------
     */
    private BigDecimal ttlExpdBox;

    /**
     * 总数量
     * NOT NULL------------
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数----------
     */
    private BigDecimal ttlBox;

    /**
     * 总包裹数-------------
     */
    private Long ttlPack;

    /**
     * 总金额---------------
     * NOT NULL
     */
    private BigDecimal ttlVal;

    /**
     * 总返利------------
     */
    private BigDecimal ttlRwd;

    /**
     * 总税款
     * NOT NULL-------------
     */
    private BigDecimal ttlTax;

    /**
     * 总市值------------
     */
    private BigDecimal ttlMkv;

    /**
     * 总成本-----------
     */
    private BigDecimal ttlCost;

    /**
     * 约定会计日期("t","f")
     * NOT NULL-------------
     */
    private String fsclDateAptd;

    /**
     * 即时结算("t","f")
     * NOT NULL-----------
     */
    private String instStl;

    /**
     * 桥接方式("d","t")
     * NOT NULL---------------
     */
    private String brdgMode;

    /**
     * 改变成本("t","f")
     * NOT NULL
     */
    private String costChg;

    /**
     * 过账控制("u","p","d")
     * NOT NULL
     */
    private String postCtrl;

    /**
     * 操作员ID
     * NOT NULL-------------
     * 登录用户id
     */
    private Long oprId;

    /**
     * 操作时间
     * NOT NULL-----------
     */
    private Date opTime;

    /**
     * 已生效("t","f")
     * NOT NULL-------------
     */
    private String effective;

    /**
     * 挂起("t","f")
     * NOT NULL-----------
     */
    private String suspended;

    /**
     * 撤销("t","f")
     * NOT NULL--------------
     */
    private String cancelled;

    /**
     * 已冲单("t","f")
     * NOT NULL
     */
    private String reversed;

    /**
     * 是否冲单("t","f")
     * NOT NULL
     */
    private String isRev;

    /**
     * 原单号
     */
    private String orgStbNum;

    /**
     * 备注----------------
     */
    private String remarks;

    /**
     * 集货区ID
     */
    private Long clnAreaId;

    /**
     * 商品明细集合
     */
    private List<StbDtlVo> stbDtlVos;
    /**
     * 操作员编号
     */
    private String oprCode;
    /**
     * 操作员名称
     */
    private String oprName;
    /**
     * 出库单编号--------------
     */
    private String gdnNum;
    /**
     *出库方式
     */
    private String delivMode;
    /**
     *会计出库方式
     */
    private String  fsclDelivMode;
    /**
     *收货方id
     */
    private String rcvUnitId;
    /**
     * 收货方编号
     */
    private String rcvUnitCode;
    /**
     * 收货方名称
     */
    private String rcvUnitName;
    /**
     * 收货方仓库id
     */
    private String rcvWarehId;
    /**
     * 收货方仓库编号
     */
    private String rcvWarehCode;
    /**
     * 收货方仓库名称
     */
    private String rcvWarehName;
    /**
     * 收货方会计组织Id
     */
    private String rcvFsclUnitId;
    /**
     * 收货方会计组织编号
     */
    private String rcvFsclUnitCode;
    /**
     * 收货方会计名称
     */
    private String rcvFsclUnitName;
    /**
     * 转送方id
     */
    private String endUnitId;
    /**
     *转送方编号
     */
    private String endUnitCode;
    /**
     *转送方名称
     */
    private String endUnitName;
    /**
     * 转送方仓库id
     */
    private String endWarehId;
    /**
     *转送方仓库编号
     */
    private String endWarehCode;
    /**
     *转送方仓库名称
     */
    private String endWarehName;
    /**
     *送货方式
     */
    private String delivMthd;
    /**
     *邮政编码
     */
    private String delivPstd;
    /**
     *送货地址
     */
    private String delivAddr;
    /**
     *启用分拣
     */
    private String pickReqd;
    /**
     *启用复核
     */
    private String rckReqd;
    /**
     *启用派车
     */
    private String vehReqd;
    /**
     *派车单编号
     */
    private String vwcNum;
    /**
     *启用托运
     */
    private String cnsnReqd;
    /**
     *托运单号
     */
    private String csbNum;
    /**
     * 拣货人id
     */
    private String ftchrId;
    /**
     *拣货人编号
     */
    private String ftchrCode;
    /**
     *拣货人名称
     */
    private String ftchrName;
    /**
     *拣货开始时间
     */
    private Timestamp ftchStTime;
    /**
     *拣货结束时间
     */
    private Timestamp ftchFinTime;
    /**
     * 发货人id
     */
    private String dlvrId;
    /**
     * 发货人编号
     */
    private String dlvrCode;
    /**
     *发货人名称
     */
    private String dlvrName;
    /**
     * 发货时间
     */
    private Timestamp delivTime;
    /**
     * 托运人id
     */
    private String cngnrId;
    /**
     *托运人编号
     */
    private String cngnrCode;
    /**
     *托运人名称
     */
    private String cngnrName;
    /**
     *托运时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp cnsnTime;
    /**
     *进度
     */
    private String progress;
}
