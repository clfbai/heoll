package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * prc_type
 * @author 
 */
@Data
public class PrcType implements Serializable {
    /**
     * 退购合同类别
     */
    private String prcType;

    /**
     * 描述
     */
    private String description;

    /**
     * 退货合同类别
     */
    private String rtcType;

    /**
     * 退购桥接方式("d","t")
     */
    private String prBrdgMode;

    /**
     * 退购桥接方式可选("t","f")
     */
    private String prBrdgModeAlt;

    /**
     * 绑定附加成本("t","f")
     */
    private String acReqd;

    /**
     * 绑定附加成本可选("t","f")
     */
    private String acReqdAlt;

    /**
     * 活动("t","f")
     */
    private String active;

    private static final long serialVersionUID = 1L;

}