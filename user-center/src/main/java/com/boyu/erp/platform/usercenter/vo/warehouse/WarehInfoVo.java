package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname WarehInfoVo
 * @Description TODO
 * @Date 2019/10/28 19:23
 * @Created by wz
 */
@Data
public class WarehInfoVo implements Serializable {

    /**
     * 原始单据组织id
     */
    private long srcDocUnitId;

    /**
     * 原始单据编号
     */
    private String srcDocNum;

    /**
     * 原始单据类别
     */
    private String srcDocType;

}
