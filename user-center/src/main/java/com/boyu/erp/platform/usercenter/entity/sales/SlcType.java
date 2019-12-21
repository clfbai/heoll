package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * slc_type
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class SlcType implements Serializable {
    /**
     * 销售合同类别
     */
    private String slcType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 销售桥接方式
     */
    private String slBrdgMode;

    /**
     * 销售桥接方式可选
     */
    private String slBrdgModeAlt;

    /**
     * 绑定附加费用
     */
    private String afReqd;

    /**
     * 绑定附加费用可选
     */
    private String afReqdAlt;

    /**
     * 活动
     */
    private String active;
}