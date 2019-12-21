package com.boyu.erp.platform.usercenter.vo.sales;


import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Classname PsoVo
 * @Description TODO
 * @Date 2019/7/17 18:28
 * @Created wz
 */
@Data
public class SloVo implements Serializable {

    /**
     * 购销单号
     */
    private String psoNum;

    /**
     * 单据日期
     */
    private String docDate;

    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 收货仓库id
     */
    private Integer rcvWarehId;

    /**
     * 发货仓库id
     */
    private Integer delivWarehId;

    /**
     * 购销单性质("t","p")
     */
    private String psoKind;

    /**
     * 采购单已生成("t","f")
     */
    private String puoGen;

    /**
     * 销售单已生成("t","f")
     */
    private String sloGen;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;
    /**
     * 货期
     */
    private String reqdDate;

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
     * 总数量
     */
    private BigDecimal ttlQty = new BigDecimal("0");

    /**
     * 总箱数
     */
    private Integer ttlBox;

    /**
     * 总金额
     */
    private BigDecimal ttlVal = new BigDecimal("0");

    /**
     * 总税款
     */
    private BigDecimal ttlTax = new BigDecimal("0");

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
    private Integer ttlDelivBox;

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
    private Integer ttlRcvBox;

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
     * 操作员id
     */
    private Integer oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 操作时间
     */
    private String opTime;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd")
     */
    private String progress;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 执行单据编号
     */
    private String execPsoNum;

    /**
     * 居间单据编号
     */
    private String itmdPsoNum;

    /**
     * 始发单据编号
     */
    private String stPsoNum;

    /**
     * 转送单据编号
     */
    private String endPsoNum;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 销售单号
     */
    private String sloNum;

    /**
     * 销售合同号
     */
    private String slcNum;

    /**
     * 收获仓库编号
     */
    private String rcvWarehNum;

    /**
     * 收获仓库名称
     */
    private String rcvWarehName;

    /**
     * 采购商编号
     */
    private String vendeeNum;
    /**
     * 采购商名称
     */
    private String vendeeName;

    /**
     * 发货仓库编号
     */
    private String delivWarehNum;

    /**
     * 发货仓库名称
     */
    private String delivWarehName;

    /**
     * 采购商介入
     */
    private String vdeInvd;

    /**
     * 挂起
     */
    private String suspended;


    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;

    /**
     * 搜索条件 结束日期
     */
    private String endTime;

    /**
     * 当前组织id
     */
    private Long unitId;

    /**
     * 采购商id
     */
    private Long vendeeId;

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
     * 审核人时间
     */
    private String chkTime;
}
