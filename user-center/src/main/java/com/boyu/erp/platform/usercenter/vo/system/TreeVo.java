package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeVo {
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
     * 是否路径
     */
    private String isPath;
    /**
     * 功能ID
     */
    private String funcId;

    /**
     * url
     */
    private String url;
    /**
     * 权限Id
     */
    private String privId;

    private List<TreeVo> setTreeVo=new ArrayList<>();
}
