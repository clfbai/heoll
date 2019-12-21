package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * erp和wms对应关系
 *
 * @author
 */
@Data
public class WmsErpNum implements Serializable {

    public WmsErpNum(String taskDocNum) {
        this.taskDocNum = taskDocNum;
    }

    public WmsErpNum() {
    }

    public WmsErpNum(String taskDocNum, String originNum, String originType) {
        this.taskDocNum = taskDocNum;
        this.originNum = originNum;
        this.originType = originType;
    }

    private static final long serialVersionUID = 1L;
    /**
     * 请求WMS单号（生成规则：源单所在领域ID+源单类型+发货仓所在领域id+源单单号）
     */
    private String taskDocNum;

    /**
     * 源单据号(入库单单号 或出库单单号)
     */
    private String originNum;

    /**
     * 源单据类型 D出库单 R入库单,
     */
    private String originType;
}