package com.boyu.erp.platform.usercenter.vo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionVo {
    /**
     * 下拉框的名称key
     */
    private String optionName;

    /**
     * 下拉框的值value
     */
    private String optionValue;
}
