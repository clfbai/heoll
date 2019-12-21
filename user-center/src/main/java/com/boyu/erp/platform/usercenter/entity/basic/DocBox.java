package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;

import java.io.Serializable;

/**
 * doc_box
 * @author 
 */
@Data
public class DocBox implements Serializable {
    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;

    /**
     * 装箱代码
     */
    private String boxCode;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 库存类别
     */
    private String stkType;
    /**
     * 已关闭
     */
    private String closed;

    private static final long serialVersionUID = 1L;

    public DocBox() {
    }

    public DocBox(String docType, Long docUnitId, String docNum, Long warehId, String stkType, String closed) {
        this.docType = docType;
        this.docUnitId = docUnitId;
        this.docNum = docNum;
        this.warehId = warehId;
        this.stkType = stkType;
        this.closed = closed;
    }
}