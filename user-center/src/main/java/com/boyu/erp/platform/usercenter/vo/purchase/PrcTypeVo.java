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
public class PrcTypeVo implements Serializable {

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
     * 退货合同类别描述
     */
    private String rtcDescription;

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
}
