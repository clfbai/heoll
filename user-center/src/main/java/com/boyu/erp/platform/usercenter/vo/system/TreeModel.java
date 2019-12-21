package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TreeModel {
    /**
     * 父节点ID
     */
    private Long fatherNodeId;

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

}
