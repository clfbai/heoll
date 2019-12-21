package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_domain
 * @author
 * 领域表pojo
 */
@Data
public class SysDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    public SysDomain() {
    }

    public SysDomain(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 领域ID
     */
    private String domainId;

    /**
     * 管理员ID
     */
    private Long saId;

    /**
     * 权限日期公式(原系统此字段都为空)
     */
    private String privDateFml;

    /**
     * 领域状态
     */
    private String domainStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 组织代码（前台传入)
     */
   private String unitCode;
    /**
     * 助记码（前台传入)
     */
    private String inputCode;

    /**
     * 组织状态（前台传入)
     */
    private String unitStatus;
    /**
     * 组织名称（前台传入)
     */
    private String unitName;
}