package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_menu
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 目录ID
     */
    private String menuId;

    /**
     * 描述
     */
    private String description;

    /**
     * 网页链接
     */
    private String url;

    /**
     * 序号
     */
    private Integer seqNum;

    /**
     * 备注
     */
    private String remarks;


}