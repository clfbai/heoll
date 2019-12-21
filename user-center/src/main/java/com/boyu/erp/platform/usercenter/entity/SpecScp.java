package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * spec_scp
 * @author
 * 规格范围
 */
@Data
public class SpecScp implements Serializable {
    /**
     * 规格范围ID
     */
    private String specScpId;

    /**
     * 规格组ID
     */
    private String specGrpId;

    /**
     * 规格组名称
     */
    private String specGrpName;

    /**
     * 规格范围名称
     */
    private String specScpName;

    /**
     * 描述
     */
    private String description;


}