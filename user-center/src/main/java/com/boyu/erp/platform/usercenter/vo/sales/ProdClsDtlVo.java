package com.boyu.erp.platform.usercenter.vo.sales;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname ProdClsVo
 * @Description TODO
 * @Date 2019/11/4 15:54
 * @Created by wz
 */
@Data
public class ProdClsDtlVo implements Serializable {

    //商品品种id
    private Long prodClsId;

    //发货数量
    private BigDecimal delivQty;

    //发货金额
    private BigDecimal delivVal;
}
