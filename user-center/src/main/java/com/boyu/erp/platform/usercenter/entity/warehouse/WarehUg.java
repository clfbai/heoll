package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * wareh_ug
 * @author 
 */
@Data
public class WarehUg implements Serializable {
    /**
     * 分组Id
     */
    private Long ugId;

    /**
     * 分组名称
     */
    private String ugName;

    /**
     * 组织分组Id
     */
    private Long sysUgId;

    /**
     * 分组编号
     */
    private String ugNum;

    /**
     * 分组类别
     */
    private String ugType;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    private static final long serialVersionUID = 1L;
}