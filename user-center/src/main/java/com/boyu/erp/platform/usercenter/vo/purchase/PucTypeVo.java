package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PucTypeVo
 * @Description TODO
 * @Date 2019/6/19 11:58
 * @Created wz
 */
@Data
public class PucTypeVo implements Serializable {

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
     * 购销合同类别描述
     */
    private String pscDescription;

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
}
