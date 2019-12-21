package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * deliv_diff_evt
 * @author 
 */
@Data
public class DelivDiffEvt implements Serializable {

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 出库单编号
     */
    private String gdnNum;
    /**
     * 处理方式
     */
    private String ddfStlMthd;

    /**
     * 进度
     */
    private String progress;

    /**
     * 处理人ID
     */
    private Long hdlrId;

    /**
     * 处理时间
     */
    private Date hdlTime;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}