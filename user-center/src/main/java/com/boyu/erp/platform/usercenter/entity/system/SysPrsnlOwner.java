package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_prsnl_owner
 *
 * @description: 人员属主表
 * @author: CLF
 * @create: 2019-5-20 11:33
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SysPrsnlOwner extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 人员编号(prsnl_code)
     */
    private String prsnlNum;

    /**
     * 人员Id
     */
    private Long prsnlId;

    /**
     * 属主Id
     */
    private Long ownerId;

}