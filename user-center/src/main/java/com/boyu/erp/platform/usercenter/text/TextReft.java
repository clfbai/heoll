package com.boyu.erp.platform.usercenter.text;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TextReft {
    /**
     * 颜色ID
     */
    private Long colorId;
    /**
     * 颜色ID
     */
    private Float colorDate;
    /**
     * 颜色代码
     */
    private Float colorCode;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 颜色值
     */
    private Long rgbVal;
}
