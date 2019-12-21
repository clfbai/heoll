package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_code_dtl
 *
 * @author
 */
@Data
@ToString
public class SysCodeDtl extends SysCodeDtlKey implements Serializable {
    /**
     * 描述
     */
    private String description;

    /**
     * 序号
     */
    private Long seqNum;
    /**
     * 修改代码(只是接受前台传入)
     */
    private String updateCode;

    public SysCodeDtl() {
    }

    public SysCodeDtl(String codeType) {
        super.setCodeType(codeType);
    }
}