package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

@Data
public class WarehDmodeModel{
    /**
     * 启用分拣
     */
    private String pickReqd;
    /**
     * 启用复核
     */
    private String rckReqd;
    /**
     * 启用装箱
     */
    private String boxReqd;
    /**
     * 预定装箱
     */
    private String boxSchd;
    /**
     * 即时结算
     */
    private String instStl;
    /**
     * 出库方式对应原始单据类别下拉
     */
//    private List<OperationVo> srcDocType;
    /**
     * 出库方式对应必填字段集合
     */
//    private List<String> requiredFields;
}
