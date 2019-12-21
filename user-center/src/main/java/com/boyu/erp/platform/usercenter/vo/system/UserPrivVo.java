package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserPrivVo implements Serializable {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 属主Id
     */
    private Long ownerId;
    /**
     * 权限Id
     */
    private String privId;
    /**
     * 分组Id
     */
    private Long ugId;
    /**
     * 是否全局（T ，F)
     */
    private String unlimited;

    private String privScp;

    private String privType;
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 权限描述
     */
    private String description;
    /**
     * 分组编号
     */
    private String ugNum;
    /**
     * 分组名称
     */
    private String ugName;
    /**
     * 组织代码
     */
    private String unitCode;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 是否全局中文名
     */
    private String unlimitedCp;
}
