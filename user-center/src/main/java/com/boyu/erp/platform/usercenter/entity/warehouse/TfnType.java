package com.boyu.erp.platform.usercenter.entity.warehouse;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * tfn_type  (调拨单类别表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class TfnType extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 调拨单类别
     */
    private String tfnType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定会计日期(T 是 F 否)
     */
    private String fsclDateReqd;

    /**
     * 差异裁定方(D 代表发货方 R代表收货方)
     */
    private String drDiffJgd;

    /**
     * 差异裁定方可选(T 是 F 否)
     */
    private String drDiffJgdAlt;

    /**
     * 启用配码(T 启用 F 不启用)
     */
    private String bxiEnabled;

    /**
     * 启用配码可选(T 启用 F 不启用)
     */
    private String bxiEnabledAlt;

    /**
     * 活动
     */
    private String active;


}