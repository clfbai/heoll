package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_code
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysCode extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代码类别
     */
    private String codeType;

    /**
     * 代码描述
     */
    private String description;
}
