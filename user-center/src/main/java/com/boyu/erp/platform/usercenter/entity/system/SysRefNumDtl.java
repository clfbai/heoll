package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_ref_num_dtl
 *
 * @author
 */
@Data
public class SysRefNumDtl extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号ID
     */
    private String refNumId;

    /**
     * 所属组织ID
     */
    private Long unitId;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 最新编号
     */
    private Long lastNum;
    /**
     * 组织名称
     */
    private String unitCode;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 修改组织Id
     */
    private Long updateUnitId;
    /**
     * 管理员Id
     */
    private int adminId;
    /**
     * 增量
     */
    private int stepSize;


    public SysRefNumDtl() {
    }

    public SysRefNumDtl(String refNumId) {
        this.refNumId = refNumId;
    }

}