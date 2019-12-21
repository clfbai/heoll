package com.boyu.erp.platform.usercenter.vo.collarUse;

import com.boyu.erp.platform.usercenter.entity.collarUse.Arq;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ArqVo
 * @Description TODO
 * @Date 2019/8/23 15:46
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class ArqVo implements Serializable {

    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 领用单编号
     */
    private String arqNum;
    /**
     * 单据日期
     */
    private String docDate;

    /**
     * 领用单类别
     */
    private String arqType;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 领用原因
     */
    private String arqRsn;

    /**
     * 货期
     */
    private String reqdDate;

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
    private Long ttlBox;

    /**
     * 总金额
     */
    private BigDecimal ttlVal;

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
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private String opTime;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private String chkTime;

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
     * 部门编号
     */
    private String deptNum;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 发货仓库编号
     */
    private String delivWarehNum;

    /**
     * 发货仓库名称
     */
    private String delivWarehName;

    /**
     * 操作人编号
     */
    private String oprNum;

    /**
     * 操作人名称
     */
    private String oprName;

    /**
     * 审核人编号
     */
    private String chkrNum;

    /**
     * 审核人名称
     */
    private String chkrName;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;

    /**
     * 搜索条件结束日期
     */
    private String endTime;

}
