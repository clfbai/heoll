package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * puc
 * @author 
 */
@Data
public class Puc implements Serializable {

    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 采购合同号
     */
    private String pucNum;
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 采购合同类别
     */
    private String pucType;

    /**
     * 采购桥接方式("d","t")
     */
    private String puBrdgMode;

    /**
     * 审核人id
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 挂起("t","f")
     */
    private String suspended;

    /**
     * 绑定附加成本("t","f")
     */
    private String acReqd;

    /**
     * 附加成本已绑定("t","f")
     */
    private String acBnd;

    /**
     * 附加成本绑定人id
     */
    private Long acBndrId;

    /**
     * 附加成本绑定时间
     */
    private Date acBndTime;

    /**
     * 销售控制("i","p","f")
     */
    private String slCtrl;

    /**
     * 已转销("t","f")
     */
    private String slFwdd;

    private static final long serialVersionUID = 1L;

}