package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * sys_menu_dtl
 *
 * @author
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 目录ID
     */
    private String menuId;

    /**
     * 描述
     */
    private String description;

    /**
     * 上级节点
     */
    private Long parnNodeId;

    /**
     * 隶属关系
     */
    private String hierarchy;

    /**
     * 是否路径
     */
    private String isPath;

    /**
     * 功能ID
     */
    private String funcId;

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

    /**
     * 是否删除
     */
    private String isDel;

    private List<SysMenuDtl> setTreeVo = new ArrayList<>();


}