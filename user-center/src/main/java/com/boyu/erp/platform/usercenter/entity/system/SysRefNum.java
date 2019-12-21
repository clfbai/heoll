package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_ref_num
 *
 * @author
 */
@Data
public class SysRefNum extends BaseData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号ID
     */
    private String refNumId;

    /**
     * 描述
     */
    private String description;

    /**
     * 起始编号
     */
    private Long fromNum;

    /**
     * 结束编号
     */
    private Long toNum;

    /**
     * 增量，默认是1
     */
    private Long stepSize;

    public SysRefNum() {
    }

    public SysRefNum(String refNumId) {
        this.refNumId = refNumId;
    }
}