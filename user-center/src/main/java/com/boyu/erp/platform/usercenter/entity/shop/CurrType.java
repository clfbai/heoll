package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * curr_type  币种
 *
 * @author
 */
@Data
@NoArgsConstructor
public class CurrType implements Serializable {
    /**
     * 币种Id
     */
    private Integer currId;
    /**
     * 币种代码
     */
    private String currCode;
    /**
     * 币种名称
     */
    private String currName;
    /**
     * 汇率
     */
    private Float exchRate;
    /**
     * 已删除
     */
    private String deleted;

    private static final long serialVersionUID = 1L;


}