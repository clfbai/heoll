package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ChaVo
 * @Description TODO
 * @Date 2019/6/21 14:58
 * @Created wz
 */
@Data
public class OptionByPsaVo implements Serializable {

    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;

    /**
     * 是否介入
     */
    private String inte;

    public OptionByPsaVo() {
    }

    public OptionByPsaVo(Long venderId, Long vendeeId) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
    }
}
