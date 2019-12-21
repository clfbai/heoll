package com.boyu.erp.platform.usercenter.model.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SysPrsnlModel implements Serializable {
    /**
     * 属主Id
     */
    private Long unitId;
    /**
     * 人员代码
     */
    private Long prsnlCode;
    /**
     * 人员名称
     */
    private String fullName;
    /**
     * 电话
     */
    private String mobileNum;
    /**
     * 人员状态
     */
    private String prsnlStatus;

    /**
     * 人员名称
     */
    private String unitHierarchy;
}
