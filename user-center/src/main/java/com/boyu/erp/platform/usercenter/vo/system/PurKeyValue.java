package com.boyu.erp.platform.usercenter.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurKeyValue {

    /**
     * 键
     */
    private String optionName;
    /**
     * 值
     */
    private String optionValue;
}
