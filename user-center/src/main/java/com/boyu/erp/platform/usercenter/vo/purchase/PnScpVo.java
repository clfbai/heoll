package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Formatter;

/**
 * @Classname PnScpVo
 * @Description TODO
 * @Date 2019/7/10 18:19
 * @Created wz
 */
@Data
public class PnScpVo implements Serializable {

    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 折率
     */
    private BigDecimal discRate;
    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 可退率
     */
    private BigDecimal rtnaRate;
}
