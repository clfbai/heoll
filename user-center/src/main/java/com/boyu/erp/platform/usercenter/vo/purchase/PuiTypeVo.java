package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PuiTypeVo
 * @Description TODO
 * @Date 2019/6/18 14:31
 * @Created wz
 */
@Data
public class PuiTypeVo implements Serializable {

    /**
     * 采购意向类别
     */
    private String puiType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销意向类别描述
     */
    private String psiDescription;

    /**
     * 活动("t","f")
     */
    private String active;
}
