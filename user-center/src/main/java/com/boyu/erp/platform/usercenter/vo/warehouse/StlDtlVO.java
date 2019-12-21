package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StlDtlVO extends StlDtl {
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 序号
     */
    private String seqNum;
    /**
     * 计量单位
     */
    private String uomCp;
    /**
     * 颜色
     */
    private String colorCp;
    /**
     * 规格
     */
    private String specCp;
    /**
     * 版姓名
     */
    private String editionCp;
    /**
     * 零售单价
     */
    private BigDecimal rtUnitPrice;
}
