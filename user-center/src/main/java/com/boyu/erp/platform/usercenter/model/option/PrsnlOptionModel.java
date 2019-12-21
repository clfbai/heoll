package com.boyu.erp.platform.usercenter.model.option;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class PrsnlOptionModel extends SysPrsnl implements Serializable {
    /**
     * 人员Id
     */
    private Long prsnlId;
    /**
     * 人员名称
     */
    private String fullName;
    /**
     * 人员代码
     */
    private String prsnlCode;
    /**
     * 办公电话
     */
    private String officeNum;
    /**
     * 移动电话
     */
    private String mobileNum;
    /**
     * 人员状态
     */
    private String prsnlStatus;
}
