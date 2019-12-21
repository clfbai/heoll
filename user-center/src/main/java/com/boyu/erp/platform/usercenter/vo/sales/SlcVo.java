package com.boyu.erp.platform.usercenter.vo.sales;


import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Classname PscVo
 * @Description TODO
 * @Date 2019/7/4 19:06
 * @Created wz
 */
@Data
public class SlcVo implements Serializable {

    /**
     * 销售合同号
     */
    private String slcNum;
    /**
     * 购销合同号
     */
    private String pscNum;
    /**
     * 单据日期
     */
    private String docDate;
    /**
     * 销售合同类别
     */
    private String slcType;
    /**
     * 购销合同类别
     */
    private String pscType;
    /**
     * 供应商ID
     */
    private String venderId;
    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;
    /**
     * 采购商id
     */
    private String vendeeId;
    /**
     * 协议控制方
     */
    private String psaCtlr;
    /**
     * 供应商仓库ID
     */
    private String vdrWarehId;
    /**
     * 供应商仓库编号
     */
    private String vdrWarehNum;
    /**
     * 供应商仓库名称
     */
    private String vdrWarehName;
    /**
     * 采购商仓库ID
     */
    private String vdeWarehId;
    /**
     * 采购商仓库编号
     */
    private String vdeWarehNum;
    /**
     * 采购商仓库名称
     */
    private String vdeWarehName;
    /**
     * 始发方ID
     */
    private String stUnitId;
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
    private String stWarehId;
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
    private String endUnitId;
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
    private String endWarehId;
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
    private String tranUnitId;
    /**
     * 中转方编号
     */
    private String tranUnitNum;
    /**
     * 中转方名称
     */
    private String tranUnitName;
    /**
     * 供应商会计组织代码
     */
    private String vdrFsclUnitId;
    /**
     * 供应商会计组织名称
     */
    private String vdrFsclUnitName;
    /**
     * 采购商会计组织代码
     */
    private String vdeFsclUnitId;
    /**
     * 采购商会计组织名称
     */
    private String vdeFsclUnitName;
    /**
     * 采购商介入("t","f")
     */
    private String vdeInvd;
    /**
     * 销售合同自动生成("t","f")
     */
    private String slcAutoGen;
    /**
     * 销售合同自动审核("t","f")
     */
    private String slcAutoChk;
    /**
     * 采购合同自动生成("t","f")
     */
    private String pucAutoGen;
    /**
     * 采购合同已生成("t","f")
     */
    private String pucGen;
    /**
     * 销售合同已生成("t","f")
     */
    private String slcGen;
    /**
     * 采购合同自动审核("t","f")
     */
    private String pucAutoChk;
    /**
     * 是否居间销售("t","f")
     */
    private String isSlItmd;
    /**
     * 是否居间采购("t","f")
     */
    private String isPuItmd;
    /**
     * 销售桥接方式("d","t")
     */
    private String slBrdgMode;
    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;
    /**
     * 是否代销("t","f")
     */
    private String isCms;
    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;
    /**
     * 多次执行("t","f")
     */
    private String multiImpl;
    /**
     * 按指令执行("t","f")
     */
    private String implByIns;
    /**
     * 是否现货("t","f")
     */
    private String isSpot;
    /**
     * 货期控制("sg","pd","SalesOrder")
     */
    private String rqdCtrl;
    /**
     * 货期
     */
    private String reqdDate;
    /**
     * 结算方式("dp","pa","pp")
     */
    private String psStlMthd;
    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;
    /**
     * 定金
     */
    private BigDecimal deposit;
    /**
     * 启用冻款("t","f")
     */
    private String mfzEnabled;
    /**
     * 启用保证金("t","f")
     */
    private String gmEnabled;
    /**
     * 保证金
     */
    private BigDecimal guaMon;
    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;
    /**
     * 冻款点("ck","ad")
     */
    private String psMfzSite;
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
     * 商品分类ID
     */
    private String prodCatId;
    /**
     * 商品分类名称
     */
    private String prodCatName;
    /**
     * 订单类别
     */
    private String ordType;

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
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Long ttlBox;

    /**
     * 总金额
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
     * 退销总数量
     */
    private BigDecimal ttlRsQty;

    /**
     * 退销总箱数
     */
    private Long ttlRsBox;

    /**
     * 退销总金额
     */
    private BigDecimal ttlRsVal;

    /**
     * 退销总税款
     */
    private BigDecimal ttlRsTax;

    /**
     * 退销总市值
     */
    private BigDecimal ttlRsMkv;

    /**
     * 退购总数量
     */
    private BigDecimal ttlRpQty;

    /**
     * 退购总箱数
     */
    private Long ttlRpBox;

    /**
     * 退购总金额
     */
    private BigDecimal ttlRpVal;

    /**
     * 退购总税款
     */
    private BigDecimal ttlRpTax;

    /**
     * 退购总市值
     */
    private BigDecimal ttlRpMkv;
    /**
     * 发货中任务数
     */
    private Long tasksInDeliv;

    /**
     * 收货中任务数
     */
    private Long tasksInRcv;
    /**
     * 预期总返利
     */
    private BigDecimal ttlExpdRwd;

    /**
     * 发货总返利
     */
    private BigDecimal ttlDelivRwd;

    /**
     * 到货总返利
     */
    private BigDecimal ttlRcvRwd;
    /**
     * 操作员id
     */
    private String oprId;
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
    private String chkrId;
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
     * 进度
     */
    private String progress;
    /**
     * 挂起("t","f")
     */
    private String suspended;
    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 已递延("t","f")
     */
    private String renewed;
    /**
     * 执行合同编号
     */
    private String execPscNum;

    /**
     * 居间合同编号
     */
    private String itmdPscNum;

    /**
     * 始发合同编号
     */
    private String stPscNum;

    /**
     * 转送合同编号
     */
    private String endPscNum;
    /**
     * 绑定附加成本("t","f")
     */
    private String afReqd;
    /**
     * 计划控制("t","f")
     */
    private String planCtrl;

    /**
     * 展会编号
     */
    private String tfrNum;
    /**
     * 绑定附加已成本("t","f")
     */
    private String afBnd;
    /**
     * 附加成本绑定人编号
     */
    private String afBndrId;
    /**
     * 附加成本绑定人名称
     */
    private String afBndrName;
    /**
     * 附加成本绑定时间
     */
    private String afBndTime;
    /**
     * 采购控制("i","p","f")
     */
    private String puCtrl;

    /**
     * 已转购("t","f")
     */
    private String puFwdd;
    /**
     * 基准合同编号
     */
    private String basePscNum;
    /**
     * 基准采购编号
     */
    private String basePucNum;
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
     * 供应商是否介入
     */
    private String vdrInvd;

    /**
     * 判断货期是否修改   默认是0  改值变1
     */
    private String reqdType = "0";
    /**
     * 货期控制修改前的值
     */
    private String rqdCtrlOld;

    /**
     * 批量添加明细
     */
    private List<PscDtlVo> pscDtlList;
}


