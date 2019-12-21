package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * src
 * @author 
 */
@Data
@NoArgsConstructor
public class Src implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 退销合同号
     */
    private String srcNum;
    /**
     * 退货合同号
     */
    private String rtcNum;

    /**
     * 退销合同类别
     */
    private String srcType;

    /**
     * 退销桥接方式
     */
    private String srBrdgMode;

    /**
     * 审核人Id
     */
    private Long chkrId;

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
    private Long afBndrId;

    /**
     * 附加费用绑定时间
     */
    private Date afBndTime;

    /**
     * 已转退购
     */
    private String prFwdd;

    private static final long serialVersionUID = 1L;

}