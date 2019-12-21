package com.boyu.erp.platform.usercenter.vo.sales;


import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Classname SrcVo
 * @Description TODO
 * @Date 2019/8/13 19:01
 * @Created wz
 */
@Data
public class SrcVo implements Serializable {


    /**
     * 采购商介入
     */
    private String vdeInvd;

    /**
     * 组织id
     */
    private Long unitId;
    /**
     * 退销合同号
     */
    private String srcNum;
    /**
     * 单据日期
     */
    private String docDate;
    /**
     * 退销合同类别
     */
    private String srcType;
    /**
     * 退货合同类别
     */
    private String rtcType;
    /**
     * 供应商ID
     */
    private Long venderId;
    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;
    /**
     * 控制协议方
     */
    private String psaCtrl;
    /**
     * 采购商仓库ID
     */
    private Long vdeWarehId;
    /**
     * 采购商仓库编号
     */
    private String vdeWarehNum;
    /**
     * 采购商仓库名称
     */
    private String vdeWarehName;
    /**
     * 供应商仓库ID
     */
    private Long vdrWarehId;
    /**
     * 供应商仓库编号
     */
    private String vdrWarehNum;
    /**
     * 供应商仓库名称
     */
    private String vdrWarehName;
    /**
     * 始发方ID
     */
    private Long stUnitId;
    /**
     * 始发方编号
     */
    private String stUnitNum;
    /**
     * 始发方名称
     */
    private String stUnitName;
    /**
     * 始发仓库ID
     */
    private Long stWarehId;
    /**
     * 始发仓库编号
     */
    private String stWarehNum;
    /**
     * 始发仓库名称
     */
    private String stWarehName;
    /**
     * 转送方ID
     */
    private Long endUnitId;
    /**
     * 转送方编号
     */
    private String endUnitNum;
    /**
     * 转送方名称
     */
    private String endUnitName;
    /**
     * 转送仓库ID
     */
    private Long endWarehId;
    /**
     * 转送仓库编号
     */
    private String endWarehNum;
    /**
     * 转送仓库名称
     */
    private String endWarehName;
    /**
     * 中转方ID
     */
    private Long tranUnitId;
    /**
     * 中转方编号
     */
    private String tranUnitNum;
    /**
     * 中转方名称
     */
    private String tranUnitName;
    /**
     * 采购商会计组织ID
     */
    private Long vdeFsclUnitId;
    /**
     * 采购商会计组织代码
     */
    private String vdeFsclUnitCode;
    /**
     * 采购商会计组织名称
     */
    private String vdeFsclUnitName;
    /**
     * 供应商会计组织ID
     */
    private Long vdrFsclUnitId;
    /**
     * 供应商会计组织代码
     */
    private String vdrFsclUnitCode;
    /**
     * 供应商会计组织名称
     */
    private String vdrFsclUnitName;
    /**
     * 供应商介入("t","f")
     */
    private String vdrInvd;
    /**
     * 退销合同自动生成("t","f")
     */
    private String srcAutoGen;
    /**
     * 退销合同自动审核("t","f")
     */
    private String srcAutoChk;
    /**
     * 退销合同已生成("t","f")
     */
    private String srcGen;
    /**
     * 退购合同自动生成("t","f")
     */
    private String prcAutoGen;
    /**
     * 退购合同自动审核("t","f")
     */
    private String prcAutoChk;
    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;
    /**
     * 货期
     */
    private String reqdDate;
    /**
     * 是否代销("t","f")
     */
    private String isCms;
    /**
     * 关联购销合同("t","f")
     */
    private String pscReqd;

    /**
     * 购销合同号
     */
    private String pscNum;
    /**
     * 退销桥接方式("d","t")
     */
    private String srBrdgMode;
    /**
     * 是否居间退购("t","f")
     */
    private String isPrItmd;
    /**
     * 是否居间退销("t","f")
     */
    private String isSrItmd;
    /**
     * 多次执行("t","f")
     */
    private String multiImpl;
    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;
    /**
     * 占用可退额度("t","f")
     */
    private String rtnaInvd;
    /**
     * 启用授信("t","f")
     */
    private String accEnabled;
    /**
     * 授信点("ck","dd")
     */
    private String rtnAccSite;
    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;
    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;
    /**
     * 授信金额
     */
    private BigDecimal accVal;
    /**
     * 原始单据类别
     */
    private String srcDocType;
    /**
     * 原始单据组织id
     */
    private Long srcDocUnitId;
    /**
     * 原始单据编号
     */
    private String srcDocNum;
    /**
     * 商品分类ID
     */
    private String prodCatId;
    /**
     * 商品分类名称
     */
    private String prodCatName;
    /**
     * 退单类别
     */
    private String rtnType;
    /**
     * 失效日期
     */
    private String expdDate;
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
     * 启用配码("t","f")
     */
    private String bxiEnabled;
    /**
     * 总数量
     */
    private BigDecimal ttlQty = new BigDecimal("0");
    /**
     * 总箱数
     */
    private Long ttlBox = 0L;
    /**
     * 总金额
     */
    private BigDecimal ttlVal= new BigDecimal("0");
    /**
     * 总税款
     */
    private BigDecimal ttlTax= new BigDecimal("0");
    /**
     * 总市值
     */
    private BigDecimal ttlMkv= new BigDecimal("0");
    /**
     * 发货总数量
     */
    private BigDecimal ttlDelivQty;
    /**
     * 发货总箱数
     */
    private Long ttlDelivBox;
    /**
     * 发货总金额
     */
    private BigDecimal ttlDelivVal;
    /**
     * 发货总税款
     */
    private BigDecimal ttlDelivTax;
    /**
     * 发货总市值
     */
    private BigDecimal ttlDelivMkv;
    /**
     * 到货总数量
     */
    private BigDecimal ttlRcvQty;
    /**
     * 到货总箱数
     */
    private Long ttlRcvBox;
    /**
     * 到货总金额
     */
    private BigDecimal ttlRcvVal;
    /**
     * 到货总税款
     */
    private BigDecimal ttlRcvTax;
    /**
     * 到货总市值
     */
    private BigDecimal ttlRcvMkv;
    /**
     * 操作员ID
     */
    private Long oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员名称
     */
    private String oprName;
    /**
     * 操作时间
     */
    private String opTime;
    /**
     * 审核人id
     */
    private Long chkrId;
    /**
     * 审核人编号
     */
    private String chkrNum;
    /**
     * 审核人名称
     */
    private String chkrName;
    /**
     * 审核时间
     */
    private String chkTime;
    /**
     * 已生效("t","f")
     */
    private String effective;
    /**
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd","cl")
     */
    private String progress;
    /**
     * 撤销("t","f")
     */
    private String cancelled;
    /**
     * 挂起("t","f")
     */
    private String suspended;
    /**
     * 执行合同编号
     */
    private String execRtcNum = "";
    /**
     * 居间合同编号
     */
    private String itmdRtcNum = "";
    /**
     * 始发合同编号
     */
    private String stRtcNum;
    /**
     * 转送合同编号
     */
    private String endRtcNum;
    /**
     * 绑定附加成本("t","f")
     */
    private String afReqd;
    /**
     * 附加成本已绑定("t","f")
     */
    private String afBnd;

    /**
     * 附加成本绑定人id
     */
    private Long afBndrId;
    /**
     * 附加成本绑定人编号
     */
    private String afBndrNum;
    /**
     * 附加成本绑定人名称
     */
    private String afBndrName;
    /**
     * 附加成本绑定时间
     */
    private String afBndTime;
    /**
     * 已转退销("t","f")
     */
    private String prFwdd;
    /**
     * 退货合同号
     */
    private String rtcNum;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 搜索条件 开始日期
     */
    private String startTime;
    /**
     * 搜索条件结束日期
     */
    private String endTime;
    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;

    /**
     * 发货中任务数
     */
    private Long tasksInDeliv = 0L;

    /**
     * 收货中任务数
     */
    private Long tasksInRcv = 0L;

    /**
     * 批量添加明细
     */
    private List<RtcDtlVo> rtcDtlList;

}
