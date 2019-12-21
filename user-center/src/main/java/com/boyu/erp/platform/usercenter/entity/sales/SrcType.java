package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * src_type 退销合同类别实体
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class SrcType implements Serializable {
    /**
     * 退销合同类别
     */
    private String srcType;

    /**
     * 描述
     */
    private String description;

    /**
     * 退货合同类别
     */
    private String rtcType;

    /**
     * 退销桥接方式
     */
    private String srBrdgMode;

    /**
     * 退销桥接方式可选
     */
    private String srBrdgModeAlt;

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