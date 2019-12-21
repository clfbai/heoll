package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * spec
 * @author
 * 规格
 */

@Data
@NoArgsConstructor
public class Spec implements Serializable {
    /**
     * 规格ID
     */
    private Long specId;

    /**
     * 规格代码
     */
    private String specCode;

    /**
     * 规格组ID
     */
    private String specGrpId;

    /**
     * 规格编号
     */
    private String specNum;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 配比
     */
    private Long proportion;

    /**
     * 描述
     */
    private String description;

    /**
     * 已删除
     */
    private String deleted;

}