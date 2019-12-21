package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * spec_scp_dtl
 * @author
 * 规格范围明细
 */
@Data
public class SpecScpDtlKey implements Serializable {
    /**
     * 规格范围ID
     */
    private String specScpId;

    /**
     * 规格ID
     */
    private Long specId;
}