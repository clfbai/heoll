package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * prc
 * @author 
 */
@Data
@NoArgsConstructor
public class Prc implements Serializable {
    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 退购合同号
     */
    private String prcNum;
    /**
     * 退货合同号
     */
    private String rtcNum;

    /**
     * 退购合同类别
     */
    private String prcType;

    /**
     * 退购桥接方式("d","t")
     */
    private String prBrdgMode;

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
     * 已转退销("t","f")
     */
    private String srFwdd;

    private static final long serialVersionUID = 1L;

}