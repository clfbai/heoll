package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.model.DomainSwitchSession;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user
 *
 * @author
 */
@Data
@ToString
public class SysUser extends SysUserKey implements Serializable {
    private static final long serialVersionUID = 1L;

    public SysUser(Long userId) {
        this.setUserId(userId);
    }

    public SysUser() {

    }

    public SysUser(Long unitId,Long prsnlId) {
        this.domain = new SysDomain(unitId);
        this.prsnl = new SysPrsnl(prsnlId);
        super.setUserId(prsnlId);
    }

    private SysPrsnl prsnl;

    /**
     * 用户密码
     */
    private String userPswd;

    /**
     * 密码过期日期
     */
    private Date upExpdDate;

    /**
     * 密码错误次数
     */
    private String upFltTms;

    /**
     * 密码重激活时间
     */
    private Date upActTime;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 机器控制
     */
    private String machCtrl;

    /**
     * 目录ID
     */
    private String menuId;

    /**
     * 权限日期公式 (原系统没有用到)
     */
    private String privDateFml;

    /**
     * 身份标识
     */
    private String idStr;

    /**
     * 用户类别
     */
    private String userType;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;


    /**
     * 领域
     */
    private SysDomain domain;


    /**
     * 用户权限集合
     */
    private List<SysPrivilege> PrivilegeSet;

    /**
     * 组织
     */
    private SysUnit unit;

    /**
     * 角色
     */
    private SysRole role;

    /**
     * 前端传入权限过滤对象
     */
    private SysPrivilege privilege = new SysPrivilege();

    /**
     * 切换领域session
     */
    private DomainSwitchSession domainSwitchSession;
    /**
     * 用户类型
     * A 系统管理员
     * L 组织管理员
     * AS 系统组织用户
     * S 普通用户
     */
    private String isType;


}