package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * spec_grp
 * @author
 * 规格组
 */
@Data
public class SpecGrp implements Serializable {
    /**
     * 规格组ID
     */
    private String specGrpId;

    /**
     * 规格组名称
     */
    private String specGrpName;

    /**
     * 描述
     */
    private String description;

}