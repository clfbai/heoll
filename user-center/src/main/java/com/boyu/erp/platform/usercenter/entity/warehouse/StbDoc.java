package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * stb_doc (库存单原始单据 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class StbDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 原始单据类别
     */
    private String srcDocType;

    /**
     * 原始单据组织ID
     */
    private Long srcDocUnitId;

    /**
     * 原始单据编号
     */
    private String srcDocNum;


}