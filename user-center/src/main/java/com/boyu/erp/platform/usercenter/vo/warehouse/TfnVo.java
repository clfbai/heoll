package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ToString
@NoArgsConstructor
public class TfnVo implements Serializable {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 调拨单号
     */
    private String tfnNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 调拨单类别
     */
    private String tfnType;

    /**
     * 会计组织ID
     */
    private Integer fsclUnitId;

    /**
     * 发货仓库ID
     */
    private Integer delivWarehId;

    /**
     * 发货会计组织ID
     */
    private Integer delivFsclUnitId;

    /**
     * 收货仓库ID
     */
    private Integer rcvWarehId;

    /**
     * 收货会计组织ID
     */
    private Integer rcvFsclUnitId;

    /**
     * 差异裁定方
     */
    private String drDiffJgd;

    /**
     * 货期
     */
    private Date reqdDate;

    /**
     * 送货方式
     */
    private String delivMthd;

    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Integer ttlBox;

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
     * 操作员ID
     */
    private Integer oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 审核人ID
     */
    private Integer chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 已生效
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 撤销
     */
    private String cancelled;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 调拨属性1
     */
    private String tfnAttr1;

    /**
     * 调拨属性2
     */
    private String tfnAttr2;

    /**
     * 组织层级ID
     */
    private String unitHierId;

    /**
     * 发货方ID
     */
    private Integer delivUnitId;

    /**
     * 收货方ID
     */
    private Integer rcvUnitId;

    /**
     * 会计组织Code
     */
    private String fsclUnitCode;

    /**
     * 会计组织名称
     */
    private String fsclUnitName;

    /**
     * 组织层级名称
     */
    private String unitHierName;

    /**
     * 发货会计组织代码
     */
    private String delivFsclUnitCode;

    /**
     * 发货会计组织名称
     */
    private String delivFsclUnitName;

    /**
     * 收货会计组织代码
     */
    private String rcvFsclUnitCode;

    /**
     * 发货会计组织名称
     */
    private String rcvFsclUnitName;

    /**
     * 发货方代码
     */
    private String delivUnitCode;

    /**
     * 发货方名称
     */
    private String delivUnitName;

    /**
     * 发货仓库代码
     */
    private String delivWarehCode;

    /**
     * 发货仓库名称
     */
    private String delivWarehName;

    /**
     * 收货方代码
     */
    private String rcvUnitCode;

    /**
     * 收货方名称
     */
    private String rcvUnitName;

    /**
     * 收货仓库代码
     */
    private String rcvWarehCode;

    /**
     * 收货仓库名称
     */
    private String rcvWarehName;

    /**
     * 操作员编号
     */
    private String oprCode;

    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 审核人编号
     */
    private String chkrCode;

    /**
     * 审核人姓名
     */
    private String chkrName;

}
