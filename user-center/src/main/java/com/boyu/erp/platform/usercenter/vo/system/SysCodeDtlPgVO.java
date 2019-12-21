package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SysCodeDtlPgVO implements Serializable {
    /**
     * 代码明细类型
     */
    private String codeType;
    /**
     * 代码明细编码
     */
    private String code;
    /**
     * 代码明细名称
     */
    private String description;

}
