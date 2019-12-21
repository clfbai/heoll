package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

import java.util.List;

/**
 * @Classname StbProdDtlVo
 * @Description TODO
 * 出入库单据中明细查询
 * @Date 2019/11/28 14:26
 * @Created by wz
 */
@Data
public class StbProdDtlVo {

    /**
     * 单据类型
     */
    private String type;

    /**
     * 单据合同号（明细编号）
     */
    private String cntrNum;

    /**
     * 商品id
     */
    private Long prodId;
}
