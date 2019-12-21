package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * pay_mode_curr  付款方式币种
 *
 * @author
 */
@Data
@NoArgsConstructor
public class PayModeCurr implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 付款方式ID
     */
    private Integer payModeId;

    /**
     * 币种ID
     */
    private Integer currId;


}