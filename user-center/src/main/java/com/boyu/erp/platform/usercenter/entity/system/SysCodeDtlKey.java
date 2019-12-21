package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_code_dtl
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class SysCodeDtlKey extends BaseData implements Serializable {
    /**
     * 代码类别
     */
    private String codeType;

    /**
     * 代码
     */
    private String code;


}