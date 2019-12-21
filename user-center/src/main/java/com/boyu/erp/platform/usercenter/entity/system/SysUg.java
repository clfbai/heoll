package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_ug
 * @author 
 */
@Data
@NoArgsConstructor
public class SysUg implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 分组ID
     */
    private Long ugId;

    /**
     * 分组名称
     */
    private String ugName;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 分组编号
     */
    private String ugNum;

    /**
     * 分组类别
     */
    private String ugType;

    /**
     * 组织类别
     */
    private String unitType;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

}