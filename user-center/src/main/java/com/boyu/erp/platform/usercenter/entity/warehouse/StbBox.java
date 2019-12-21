package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * stb_box (库存单装箱 表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class StbBox implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 装箱代码
     */
    private String boxCode;
    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 预期箱数
     */
    private BigDecimal expdBox;

    /**
     * 箱数
     */
    private BigDecimal box;

}