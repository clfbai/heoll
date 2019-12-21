package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * system_operation_log
 * 操作日志表
 *
 * @author clf
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SystemOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户组织Id
     */
    private Long unitId;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 操作描述
     */
    private String describe;
    /**
     * 用户请求Ip地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private String requestparameter;

    /**
     * 返回值
     */
    private String responsevalue;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 切换领域用户Id
     */
    private Long cutDomainUserId;
    /**
     * 是否切换领域登陆
     */
    private String cutDomain;
    /**
     * 切换领域用户组织Id
     */
    private Long cutDomainUnitId;
}