package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehUnitOptionVo implements Serializable {
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 仓库Id
     */
    private Long warehId;
    /**
     * 组织代码码
     */
    private String unitCode;
    /**
     * 组织编号
     */
    private String unitNum;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 联系电话
     */
    private String telNum;
    /**
     * 地址
     */
    private String emailAddr;
    /**
     * 组织状态
     */
    private String unitStatus;
    /**
     * 人员名称
     */
    private String fullName;
    /**
     * 管理组织代码
     */
    private String adUnitCode;
    /**
     * 管理组织名称
     */
    private String adUnitName;
}
