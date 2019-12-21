package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * slc
 * @author 
 */
@Data
@NoArgsConstructor
public class Slc implements Serializable {
    /**
     * 组织ID
     */
    private Integer unitId;

    /**
     * 销售合同号
     */
    private String slcNum;
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 销售合同类别
     */
    private String slcType;

    /**
     * 销售桥接方式
     */
    private String slBrdgMode;

    /**
     * 审核人ID
     */
    private Integer chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 绑定附加费用
     */
    private String afReqd;

    /**
     * 附加费用已绑定
     */
    private String afBnd;

    /**
     * 附加费用绑定人ID
     */
    private Integer afBndrId;

    /**
     * 附加费用绑定时间
     */
    private Date afBndTime;

    /**
     * 采购控制
     */
    private String puCtrl;

    /**
     * 已转购
     */
    private String puFwdd;

    private static final long serialVersionUID = 1L;

}