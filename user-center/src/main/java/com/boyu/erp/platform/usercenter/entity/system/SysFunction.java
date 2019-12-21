package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_function
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysFunction implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 功能ID
     */
    private String funcId;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限自检
     */
    private String privSelfChk;

    /**
     * 权限ID
     */
    private String privId;

    /**
     * 实现模块
     */
    private String module;

    /**
     * 实现方法
     */
    private String method;

    /**
     * 参数串
     */
    private String parmStr;

    /**
     * 图标路径
     */
    private String iconPath;

    /**
     * 功能类别
     */
    private String funcType;

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public String getFuncType() {
        return funcType==""?null:funcType;
    }

    /**
     * 权限名称
     */
    private String descriptionName;


    private Long userId;

    private Long ownerId;

}