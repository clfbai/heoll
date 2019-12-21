package com.boyu.erp.platform.usercenter.entity.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * color
 * @author
 * 颜色
 */
@Data
public class Color implements Serializable {
    /**
     * 颜色ID
     */
    private Long colorId;

    /**
     * 颜色代码
     */
    private String colorCode;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 颜色值
     */
    private Long rgbVal;

    /**
     * 已删除
     */
    private String deleted;


}