package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * puc_type
 * @author 
 */
@Data
public class PucType implements Serializable {
    /**
     * 采购合同类别
     */
    private String pucType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 采购桥接方式("d","t")
     */
    private String puBrdgMode;

    /**
     * 采购桥接方式可选("t","f")
     */
    private String puBrdgModeAlt;

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